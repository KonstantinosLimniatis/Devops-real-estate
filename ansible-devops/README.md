# Ansible Deployment Guide

This folder contains Ansible inventory, variables, and playbooks used to deploy the project in VM and Docker environments.

## Structure

```text
ansible-devops/
├── ansible.cfg
├── hosts.yaml
├── group_vars/
│   ├── all.yaml
│   ├── appservers.yaml
│   └── dbservers.yaml
├── host_vars/
│   ├── appserver-vm.yaml
│   ├── dbserver-vm.yaml
│   └── gcloud-app-server.yaml
└── playbooks/
    ├── postgres-16.yaml
    ├── spring.yaml
    ├── docker.yaml
    └── deploy-all.yaml
```

## Inventory

`hosts.yaml` defines three main targets:
- `dbserver-vm`: PostgreSQL VM
- `appserver-vm`: Spring + Nginx VM
- `docker-vm`: Docker host (runs docker compose)

Before running playbooks, update:
- `ansible_host`
- `ansible_user`
- `ansible_ssh_private_key_file`

## Variables

- Common vars: `group_vars/all.yaml`
- Group defaults:
  - `group_vars/appservers.yaml`
  - `group_vars/dbservers.yaml`
- Host overrides:
  - `host_vars/appserver-vm.yaml`
  - `host_vars/dbserver-vm.yaml`

Ansible precedence applies: `host_vars` override `group_vars`.

## Prerequisites

On control machine:
- Ansible installed
- SSH access to all target VMs
- Python available on target VMs (`/usr/bin/python3`)

## Connectivity Check

Run from this folder:

```bash
cd ansible-devops
ansible -i hosts.yaml dbserver-vm -m ping
ansible -i hosts.yaml appserver-vm -m ping
ansible -i hosts.yaml docker-vm -m ping
```

## Deployment Scenarios

### 1) VM mode (PostgreSQL VM + Spring VM + Nginx)

```bash
ansible-playbook -i hosts.yaml -l dbserver-vm playbooks/postgres-16.yaml
ansible-playbook -i hosts.yaml -l appserver-vm playbooks/spring.yaml
```

Verify Spring service:

```bash
ansible -i hosts.yaml appserver-vm -b -m shell -a "systemctl is-active spring"
```

### 2) Docker mode (single Docker VM)

```bash
ansible-playbook -i hosts.yaml -l docker-vm playbooks/docker.yaml
```

Verify containers:

```bash
ansible -i hosts.yaml docker-vm -m shell -a "docker ps"
```

### 3) Full run (all playbooks)

`deploy-all.yaml` imports all three playbooks:
- `postgres-16.yaml`
- `spring.yaml`
- `docker.yaml`

```bash
ansible-playbook -i hosts.yaml playbooks/deploy-all.yaml
```

## Syntax Check

If your environment needs writable local temp:

```bash
mkdir -p /tmp/ansible-local
ANSIBLE_LOCAL_TEMP=/tmp/ansible-local ansible-playbook -i hosts.yaml playbooks/deploy-all.yaml --syntax-check
```

## Troubleshooting

Check Spring logs on VM mode:

```bash
ansible -i hosts.yaml appserver-vm -b -m shell -a "journalctl -u spring -n 120 --no-pager"
```

Check Docker logs on docker-vm:

```bash
ansible -i hosts.yaml docker-vm -m shell -a "docker logs --tail 120 re-backend"
```

Check listening ports:

```bash
ansible -i hosts.yaml docker-vm -m shell -a "ss -ltnp | grep -E ':8080|:5432|:5433'"
```
