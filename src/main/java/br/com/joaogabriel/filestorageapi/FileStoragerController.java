package br.com.joaogabriel.filestorageapi;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/files")
public class FileStoragerController {
    private final Path fileStorageLocation;

    public FileStoragerController(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
        /*uso a classe paths para pegar o diretorio de upload e transformo em um caminho absoluto (era relativo)
            e normalizo para tirar possiveis .. ;
        */
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            Path targetLocation = fileStorageLocation.resolve(fileName);
            file.transferTo(targetLocation);

            String fileDonwloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("api/files/download/")
                    .path(fileName)
                    .toUriString();

            return ResponseEntity.ok("Upload completed! Download Link: " + fileDonwloadUri);
        } catch (IOException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    /*usando :.+ para considerar o ponto do arquivo*/
    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws IOException {
        Path filePath = fileStorageLocation.resolve(fileName).normalize();

        try {
            Resource resource = new UrlResource(filePath.toUri());
            String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

            if (contentType == null)
                contentType = "application/octec-stream";

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<String>> listFiles() throws IOException {
        List<String> fileNames = Files.list(fileStorageLocation)
                .map(Path::getFileName)
                .map(Path::toString)
                .collect(Collectors.toList());

        return ResponseEntity.ok(fileNames);
    }
}
