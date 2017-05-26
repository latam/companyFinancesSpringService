package pl.mlata.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mlata.persistance.model.VatRegistryEntry;
import pl.mlata.service.VatRegistryService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/vatRegistry")
public class VatRegistryController {

    @Autowired
    private VatRegistryService vatRegistryService;


    @RequestMapping(value = "/entries/{month}/{year}", method = RequestMethod.GET)
    public ResponseEntity<List<VatRegistryEntry>> getRegistry(@PathVariable Integer month, @PathVariable Integer year) {
        List<VatRegistryEntry> entries = vatRegistryService.getRegistryEntries(month, year);
        return new ResponseEntity<>(entries, HttpStatus.OK);
    }

    @RequestMapping(value = "/report/{registryId}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getRegistryReport(@PathVariable Long registryId) {
        byte[] pdfReport = vatRegistryService.generateReportForId(registryId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        String filename = "VATRegistry_" + LocalDate.now().getYear() + "_" + LocalDate.now().getMonthValue() + ".pdf";
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<>(pdfReport, headers, HttpStatus.OK);
        return response;
    }

    @RequestMapping(value = "/report/{month}/{year}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getRegistryReportForPeriod(@PathVariable Integer month, @PathVariable Integer year) {
        byte[] pdfReport = vatRegistryService.generateReportForMonthAndYear(month, year);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        String filename = "VATRegistry_" + month + "_" + year + ".pdf";
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<>(pdfReport, headers, HttpStatus.OK);
        return response;
    }
}
