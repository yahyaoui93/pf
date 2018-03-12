package io.maxilog.controller;


import io.maxilog.entity.User;
import io.maxilog.security.JwtUser;
import io.maxilog.service.StorageService;
import io.maxilog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by mossa on 02/12/2017.
 */
@RestController
public class UploadController {

    @Autowired
    private StorageService storageService;

    @Autowired
    private UserService userService;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "users/upload",headers=("content-type=multipart/*"), method = RequestMethod.POST)
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, Authentication authentication) {

        if (file.isEmpty()) {
            return new ResponseEntity("please select a file!", HttpStatus.BAD_REQUEST);
        }
        String FileName = storageService.store(file);
        Object principal = authentication.getPrincipal();
        JwtUser jwtUser = (JwtUser)principal;
        User user = userService.findByEmail(jwtUser.getEmail());
        user.setPhoto(FileName);
        userService.update(user);
        return ResponseEntity.ok(true);

    }

    @RequestMapping(value = "/uploads/{filename:.+}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = storageService.loadFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
}