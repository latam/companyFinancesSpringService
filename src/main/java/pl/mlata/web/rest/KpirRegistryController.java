package pl.mlata.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.mlata.persistance.model.KpirRegistry;
import pl.mlata.persistance.model.KpirRegistryEntry;
import pl.mlata.reports.KpirRegistryReport;
import pl.mlata.service.KpirRegistryService;


import java.util.List;

@RestController
@RequestMapping(value = "/kpirRegistry")
public class KpirRegistryController {
    @Autowired
    KpirRegistryService kpirRegistryService;

    @RequestMapping(value = "/{month}/{year}", method = RequestMethod.GET)
    public ResponseEntity<List<KpirRegistryEntry>> getRegistry(@PathVariable Integer month, @PathVariable Integer year) {
        List<KpirRegistryEntry> entries = kpirRegistryService.getRegistryEntries(month, year);
        return new ResponseEntity<>(entries, HttpStatus.OK);
    }

    @RequestMapping(value = "/report/{year}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getRegistryReport(@PathVariable Integer year) {
        List<KpirRegistry> kpirRegistry = kpirRegistryService.getRegistries(year);

        KpirRegistryReport kpirRegistryReport = new KpirRegistryReport();
        byte[] pdfReport = kpirRegistryReport.createReport(kpirRegistry);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        String filename = "KPIRRegistry_" + year + ".pdf";
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<>(pdfReport, headers, HttpStatus.OK);
        return response;
    }
}
