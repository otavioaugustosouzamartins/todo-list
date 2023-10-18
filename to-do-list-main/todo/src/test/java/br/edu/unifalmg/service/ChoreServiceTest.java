package br.edu.unifalmg.service;

import br.edu.unifalmg.domain.Chore;
import br.edu.unifalmg.exception.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.zip.DataFormatException;

import static org.junit.jupiter.api.Assertions.*;

public class ChoreServiceTest {
    @Test
    void addChoreWhenTheDescriptionIsInvalidThrowAnException() {
        ChoreService service = new ChoreService();
        assertAll(
                () -> assertThrows(InvalidDescriptionException.class,
                        () -> service.addChore(null, null)),
                () -> assertThrows(InvalidDescriptionException.class,
                        () -> service.addChore("", null)),
                () -> assertThrows(InvalidDescriptionException.class,
                        () -> service.addChore(null, LocalDate.now().plusDays(1))),
                () -> assertThrows(InvalidDescriptionException.class,
                        () -> service.addChore("", LocalDate.now().plusDays(1))),
                () -> assertThrows(InvalidDescriptionException.class,
                        () -> service.addChore(null, LocalDate.now().minusDays(1))),
                () -> assertThrows(InvalidDescriptionException.class,
                        () -> service.addChore("", LocalDate.now().minusDays(1)))
        );
    }

    @Test
    @DisplayName("#addChore > When the deadline is invalid > Throw an exception")
    void addWhenTheDeadlineIsInvalidThrowAnException () {
        ChoreService service = new ChoreService();
        assertAll(
                () -> assertThrows(InvalidDeadlineException.class,
                        () -> service.addChore("Description",null)),
                () -> assertThrows(InvalidDeadlineException.class,
                        () -> service.addChore("Description",LocalDate.now().minusDays(1)))
        );
    }

    @Test
    @DisplayName("#add > When the adding a chore > When the chore already exists > Throw an exception")
    void addWhenTheAddingAChoreWhenTheChoreAlreadyExistsThrowAnException () {
        ChoreService service = new ChoreService();
        service.addChore("Description", LocalDate.now());
        assertThrows(DuplicatedChoreException.class,
                () -> service.addChore("Description", LocalDate.now())
                );
    }

    @Test
    @DisplayName("#deleteChore > When the list is empty throw an exception")
    void deleteChoreWhenTheListIsEmptyThrowAnException () {
        ChoreService service = new ChoreService();
        assertThrows(EmptyChoreListException.class, () -> {
           service.deleteChore("Qualquer coisa", LocalDate.now());
        });
    }

    @Test
    @DisplayName("deleteChore > When The List Is Not Empty > When The Chore Does Exist Throw An Exception")
    void deleteChoreWhenTheListIsNotEmptyWhenTheChoreDoesExistThrowAnException () {
        ChoreService service = new ChoreService();
        service.addChore("Description", LocalDate.now());
        assertThrows(ChoreNotFoundException.class, () -> {
           service.deleteChore("Chore to be deleted", LocalDate.now().plusDays(5));
        });
    }
    @Test
    @DisplayName("deleteChore > When the list is not empty > When the chore exists delete the chore")
    void deleteChoreWhenTheListIsNotEmptyWhenTheChoreExistsDeleteTheChore() {
        ChoreService service = new ChoreService();

        service.addChore("Chore #01", LocalDate.now().plusDays(1));
        assertEquals(1, service.getChores().size());

        Assertions.assertDoesNotThrow(() -> service.deleteChore("Chore #01", LocalDate.now().plusDays(1)));
        assertEquals(0, service.getChores().size());

    }
}