package by.jawh.profilemicroservice.controller;

import by.jawh.profilemicroservice.business.dto.ProfileRequestDto;
import by.jawh.profilemicroservice.business.service.MinioService;
import by.jawh.profilemicroservice.business.service.ProfileServiceImpl;
import by.jawh.profilemicroservice.common.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profiles")
public class ProfileController {

    private final ProfileServiceImpl profileService;
    private final ProfileRepository profileRepository;
    private final MinioService minioService;

    @GetMapping()
    public ResponseEntity<?> findAllProfiles() {
        return ResponseEntity.ok().body(profileService.findAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(profileService.findById(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProfile(@PathVariable Long id) {
        return ResponseEntity.ok().body(profileService.deleteProfile(id));
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<?> updateProfile(
            @PathVariable("id") Long id,
            @RequestBody ProfileRequestDto profileRequestDto) {

        return ResponseEntity.ok().body(
                profileService.editProfile(id, profileRequestDto));
    }

    @PostMapping("/emailExist")
    public ResponseEntity<Boolean> checkEmail(@RequestBody String email) {
        if (profileRepository.findByEmail(email).isEmpty())
            return ResponseEntity.ok().body(true);
        else if (profileRepository.findByEmail(email).isPresent())
            return ResponseEntity.ok().body(false);
        else
            throw new RuntimeException("реузльтат find by email не null и не какое то значение");
    }


    @PostMapping("/{id}/avatar")
    public ResponseEntity<?> uploadAvatar(@PathVariable("id") Long id,
                                          @RequestParam("avatar") MultipartFile file) {

        try {
            return ResponseEntity.ok().body(
                    "Avatar updated successfully, new URL: " +
                    profileService.uploadAvatar(id, file));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error occurred: " + e.getMessage());
        }
    }
}
