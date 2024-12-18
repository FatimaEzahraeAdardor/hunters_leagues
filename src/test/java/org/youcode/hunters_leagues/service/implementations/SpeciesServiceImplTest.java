package org.youcode.hunters_leagues.service.implementations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.youcode.hunters_leagues.domain.Species;
import org.youcode.hunters_leagues.domain.enums.Difficulty;
import org.youcode.hunters_leagues.domain.enums.SpeciesType;
import org.youcode.hunters_leagues.repository.SpeciesRepository;
import org.youcode.hunters_leagues.web.exception.species.InvalidSpeciesExeption;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SpeciesServiceImplTest {

    @Mock
    private SpeciesRepository speciesRepository;

    @InjectMocks
    private SpeciesServiceImpl speciesService;

    private Species species;
    private UUID speciesId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        speciesId = UUID.randomUUID();
        species = new Species();
        species.setId(speciesId);
        species.setName("Lion");
        species.setCategory(SpeciesType.SEA);
        species.setMinimumWeight(150.0);
        species.setDifficulty(Difficulty.COMMON);
        species.setPoints(10);
    }

    @Test
    void save_shouldSaveSpecies() {
        when(speciesRepository.save(species)).thenReturn(species);

        Species savedSpecies = speciesService.save(species);

        assertNotNull(savedSpecies);
        assertEquals(species.getName(), savedSpecies.getName());
        verify(speciesRepository, times(1)).save(species);
    }

    @Test
    void save_shouldThrowExceptionWhenSpeciesIsNull() {
        assertThrows(InvalidSpeciesExeption.class, () -> speciesService.save(null));
        verify(speciesRepository, never()).save(any(Species.class));
    }

    @Test
    void update_shouldUpdateExistingSpecies() {
        when(speciesRepository.findById(speciesId)).thenReturn(Optional.of(species));
        when(speciesRepository.save(species)).thenReturn(species);

        Species updatedSpecies = new Species();
        updatedSpecies.setId(speciesId);
        updatedSpecies.setName("Elephant");
        updatedSpecies.setCategory(SpeciesType.BIRD);
        updatedSpecies.setMinimumWeight(1000.0);
        species.setDifficulty(Difficulty.COMMON);
        updatedSpecies.setPoints(15);

        Species result = speciesService.update(updatedSpecies);

        assertEquals("Elephant", result.getName());
        assertEquals(1000.0, result.getMinimumWeight());
        verify(speciesRepository, times(1)).findById(speciesId);
        verify(speciesRepository, times(1)).save(species);
    }

    @Test
    void update_shouldThrowExceptionWhenSpeciesNotFound() {
        when(speciesRepository.findById(speciesId)).thenReturn(Optional.empty());

        Species updatedSpecies = new Species();
        updatedSpecies.setId(speciesId);

        assertThrows(InvalidSpeciesExeption.class, () -> speciesService.update(updatedSpecies));
        verify(speciesRepository, times(1)).findById(speciesId);
    }

    @Test
    void delete_shouldDeleteSpecies() {
        when(speciesRepository.findById(speciesId)).thenReturn(Optional.of(species));

        boolean result = speciesService.delete(speciesId);

        assertTrue(result);
        verify(speciesRepository, times(1)).findById(speciesId);
        verify(speciesRepository, times(1)).delete(species);
    }

    @Test
    void delete_shouldThrowExceptionWhenSpeciesNotFound() {
        when(speciesRepository.findById(speciesId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> speciesService.delete(speciesId));
        verify(speciesRepository, times(1)).findById(speciesId);
    }

    @Test
    void findById_shouldReturnSpecies() {
        when(speciesRepository.findById(speciesId)).thenReturn(Optional.of(species));

        Species foundSpecies = speciesService.findById(speciesId);

        assertNotNull(foundSpecies);
        assertEquals(speciesId, foundSpecies.getId());
        verify(speciesRepository, times(1)).findById(speciesId);
    }

    @Test
    void findById_shouldThrowExceptionWhenSpeciesNotFound() {
        when(speciesRepository.findById(speciesId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> speciesService.findById(speciesId));
        verify(speciesRepository, times(1)).findById(speciesId);
    }

    @Test
    void getAllSpeciesPaginated_shouldReturnPagedSpecies() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Species> page = new PageImpl<>(Arrays.asList(species));
        when(speciesRepository.findAll(pageRequest)).thenReturn(page);

        Page<Species> result = speciesService.getAllSpeciesPaginated(0, 10);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(speciesRepository, times(1)).findAll(pageRequest);
    }


}
