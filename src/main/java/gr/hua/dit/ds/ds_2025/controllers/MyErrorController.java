package gr.hua.dit.ds.ds_2025.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) { // Μέθοδος που διαχειρίζεται τα errors
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE); // Λαμβάνουμε το error

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString()); // Μετατροπή του error σε integer

            if(statusCode == HttpStatus.NOT_FOUND.value()) { // Αν έχουμε error 404(NOT FOUND)
                return "error/error-404"; // Επιστρέφεται το αντίστοιχο template
            }
            else if(statusCode == HttpStatus.FORBIDDEN.value()) { // Αν έχουμε error 403(FORBIDDEN)
                return "error/error-403"; // Επιστρέφεται το αντίστοιχο template
            }
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) { // Αν έχουμε error 500(INTERNAL SERVER ERROR)
                return "error/error-500"; // Επιστρέφεται το αντίστοιχο template
            }
        }
        return "error/error"; // Επιστρέφεται ένα γενικό template σε περίπτωση που έχουμε κάποιο error διαφορετικό από τα παραπάνω
    }
}