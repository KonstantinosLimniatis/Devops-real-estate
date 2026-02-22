# Real Estate DevOps Project

Εργασία για το μάθημα **«Βασικές έννοιες και εργαλεία DevOps»**.

Το project υλοποιεί πληροφοριακό σύστημα διαχείρισης αγγελιών ακινήτων με:
- backend εφαρμογή σε Spring Boot,
- PostgreSQL βάση,
- σύστημα χρηστών/ρόλων (admin περιβάλλον),
- αποστολή email σε επιτυχή εγγραφή,
- Dockerized εκτέλεση,
- Ansible αυτοματοποίηση deployment.

## 1. Υλοποίηση σε σχέση με την εκφώνηση

### Υλοποιημένα
- Βάση δεδομένων: PostgreSQL
- Σύστημα διαχείρισης χρηστών (admin): διαθέσιμο μέσω ρόλων/σελίδων χρηστών
- Βασικό πληροφοριακό σύστημα: real-estate web app
- Σύστημα ενημέρωσης με email: ενεργό (registration success email)
- Docker + Docker Compose
- Ansible deployment σε:
  - VM περιβάλλον (native service + nginx)
  - Docker περιβάλλον (docker compose από ansible)

### Μη υλοποιημένα σε αυτή την έκδοση
- Jenkins CI/CD pipeline
- Kubernetes deployment
- Object storage (προαιρετικό στην εκφώνηση)

## 2. Τεχνολογίες

- Java 21
- Spring Boot 3
- Spring Security
- Thymeleaf
- PostgreSQL 16
- Docker / Docker Compose
- Ansible
- Nginx (στο VM deployment mode)

## 3. Αρχιτεκτονική

Το σύστημα αποτελείται από:
1. `spring` service (web app, business logic, auth, email integration)
2. `db` service (PostgreSQL)
3. Admin λειτουργίες χρηστών/ρόλων μέσα στην εφαρμογή
4. SMTP integration για αποστολή email κατά την εγγραφή

## 4. Repository δομή (monorepo)

```text
.
├── src/                      # Spring Boot εφαρμογή
├── docker/
│   ├── docker-compose.yml    # DB + app με healthchecks
│   ├── backend.env.example   # template env (χωρίς πραγματικά secrets)
│   └── backend.env           # local secrets (ignored από git)
├── ansible-devops/
│   ├── playbooks/
│   ├── group_vars/
│   ├── host_vars/
│   └── hosts.yaml
└── Dockerfile
```

## 5. Ασφάλεια secrets

Τα πραγματικά credentials **δεν πρέπει να γίνονται commit**.

### Χρησιμοποιούνται templates
- `docker/backend.env.example`
- `ansible-devops/host_vars/appserver-vm.secrets.example.yaml`
- `ansible-devops/host_vars/dbserver-vm.secrets.example.yaml`
- `ansible-devops/group_vars/appservers.secrets.example.yaml`

### Τοπικά secrets (ignored)
- `docker/backend.env`
- `ansible-devops/host_vars/appserver-vm.secrets.yaml`
- `ansible-devops/host_vars/dbserver-vm.secrets.yaml`
- `ansible-devops/group_vars/appservers.secrets.yaml`

## 6. Τοπικό run με Docker Compose

Από root του repo:

```bash
cp docker/backend.env.example docker/backend.env
```

Συμπληρώνεις πραγματικές τιμές στο `docker/backend.env` (DB + SMTP), και μετά:

```bash
cd docker
docker compose up -d --build
```

Έλεγχος:
- App: `http://localhost:8080`
- Health endpoint: `http://localhost:8080/actuator/health`

Stop:

```bash
docker compose down
```

## 7. Ansible deployment

### 7.1 VM deployment (PostgreSQL + Spring service + Nginx)

Από `ansible-devops/`:

```bash
cp host_vars/appserver-vm.secrets.example.yaml host_vars/appserver-vm.secrets.yaml
cp host_vars/dbserver-vm.secrets.example.yaml host_vars/dbserver-vm.secrets.yaml
```

Συμπλήρωσε πραγματικές τιμές DB στα:
- `host_vars/appserver-vm.secrets.yaml`
- `host_vars/dbserver-vm.secrets.yaml`

Έπειτα:

```bash
ansible-playbook -i hosts.yaml -l dbserver-vm playbooks/postgres-16.yaml
ansible-playbook -i hosts.yaml -l appserver-vm playbooks/spring.yaml
```

### 7.2 Docker deployment μέσω Ansible

Από `ansible-devops/`:

```bash
cp group_vars/appservers.secrets.example.yaml group_vars/appservers.secrets.yaml
```

Το playbook:
- `ansible-devops/playbooks/spring-vue-docker.yaml`

εκτελεί setup Docker/Compose στο target host και κάνει `docker compose up -d --build`.

## 8. Email λειτουργία

Η εφαρμογή στέλνει email επιτυχούς εγγραφής χρήστη.

- Service: `src/main/java/gr/hua/dit/ds/ds_2025/services/EmailService.java`
- Trigger στη δημιουργία χρήστη: `src/main/java/gr/hua/dit/ds/ds_2025/services/UserService.java`
- Template email: `src/main/resources/templates/email/registration-success.html`

Απαραίτητες μεταβλητές:
- `SPRING_MAIL_HOST`
- `SPRING_MAIL_PORT`
- `SPRING_MAIL_USERNAME`
- `SPRING_MAIL_PASSWORD`

## 9. Authentication / Roles

Ρόλοι:
- `ROLE_USER`
- `ROLE_OWNER`
- `ROLE_ADMIN`

Βασικά routes:
- Login: `/login`
- Register: `/register`
- Μετά το login: `/realestate`
- User admin views: `/users` κ.ά.

## 10. Build / test

```bash
./mvnw clean package -DskipTests
```

ή με tests:

```bash
./mvnw test
```

## 11. Παρατηρήσεις για την παρουσίαση

Το παρόν παραδοτέο καλύπτει τον πυρήνα της εκφώνησης σε:
- εφαρμογή,
- Docker,
- Ansible,
- email integration.

Στο scope αυτής της έκδοσης δεν περιλαμβάνονται Jenkins και Kubernetes.
