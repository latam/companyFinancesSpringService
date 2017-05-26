package pl.mlata.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mlata.persistance.model.Operation;
import pl.mlata.service.OperationService;

import java.time.LocalDate;
import java.util.List;

@RestController
public class OperationController {
    @Autowired
    private OperationService operationService;

    @RequestMapping(value = "/operation", method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveOperation(@RequestBody Operation operation) {
        operationService.addOperation(operation);
    }

    @RequestMapping(value = "/operation", params = { "from", "to" }, method = RequestMethod.GET, consumes = "application/json")
    public ResponseEntity<List<Operation>> getOperations(
            @RequestParam( "from" ) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
            @RequestParam( "to" ) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate) {
        return new ResponseEntity<>(operationService.getFilteredOperations(fromDate, toDate), HttpStatus.OK);
    }

    @RequestMapping(value = "/operation/{operationId}", method = RequestMethod.DELETE, consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void deleteOperation(@PathVariable Long operationId) {
        operationService.deleteOperation(operationId);
    }
}
