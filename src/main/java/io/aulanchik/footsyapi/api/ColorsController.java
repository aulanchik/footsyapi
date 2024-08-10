package io.aulanchik.footsyapi.api;

import io.aulanchik.footsyapi.entities.Color;
import io.aulanchik.footsyapi.repositories.ColorRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/colors")
public class ColorsController {

    private final ColorRepository colorRepository;

    @GetMapping
    public ResponseEntity<Page<Color>> getAllColors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Color> colorPage = colorRepository.findAll(pageable);
        return ResponseEntity.ok(colorPage);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Color>> getByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Color> colorPage = colorRepository.findByNameContainingIgnoreCase(name, pageable);
        return ResponseEntity.ok(colorPage);
    }

    @PostMapping
    public ResponseEntity<List<Color>> post(@RequestBody List<Color> colors) {
        List<Color> savedColors = colorRepository.saveAll(colors);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedColors);
    }
}
