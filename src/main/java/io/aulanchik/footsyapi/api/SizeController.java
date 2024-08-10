package io.aulanchik.footsyapi.api;

import io.aulanchik.footsyapi.entities.BaseEntity;
import io.aulanchik.footsyapi.entities.Size;
import io.aulanchik.footsyapi.repositories.SizeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/sizes")
public class SizeController extends BaseEntity {

    private final SizeRepository sizeRepository;

    @GetMapping
    public ResponseEntity<Page<Size>> getAllSizes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Size> sizePage = sizeRepository.findAll(pageable);
        return ResponseEntity.ok(sizePage);
    }
}
