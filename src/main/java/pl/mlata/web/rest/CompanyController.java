package pl.mlata.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mlata.persistance.model.Company;
import pl.mlata.service.CompanyService;

import java.util.List;

@RestController
@RequestMapping(value = "/company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @RequestMapping(method = RequestMethod.GET, consumes = "application/json")
    public ResponseEntity<Company> getCompany() {
        return new ResponseEntity<>(companyService.getUserCompany(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity<Company> updateCompany(@RequestBody Company company) {
        Company updatedCompany = companyService.updateCompany(company);
        return new ResponseEntity<>(updatedCompany, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveCompany(@RequestBody Company company) {
        companyService.saveCompany(company);
    }


    @RequestMapping(value = "/contractors", method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveContractors(@RequestBody Company company) {
        companyService.saveContractor(company);
    }

    @RequestMapping(value = "/contractors", method = RequestMethod.GET, consumes = "application/json")
    public ResponseEntity<List<Company>> getContractors() {
        return new ResponseEntity<>(companyService.getContractors(), HttpStatus.OK);
    }
}
