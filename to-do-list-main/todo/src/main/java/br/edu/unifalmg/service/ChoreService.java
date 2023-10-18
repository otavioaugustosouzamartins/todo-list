package br.edu.unifalmg.service;

import br.edu.unifalmg.domain.Chore;
import br.edu.unifalmg.exception.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChoreService {

    private List<Chore> chores;

    public ChoreService() {
        chores = new ArrayList<>();
    }

    public List<Chore> getChores() {
        return chores;
    }

    public Chore addChore(String description, LocalDate deadline) {
        if (description == null || description.isEmpty()) {
            throw new InvalidDescriptionException("The description cannot be null or empty");
        }
        if (deadline == null || deadline.isBefore(LocalDate.now())) {
            throw new InvalidDeadlineException("The deadline cannot be or before the current date");
        }

        for (Chore chore : chores) {
            if (chore.getDescription().equals(description) && chore.getDeadline().isEqual(deadline)){
                throw new DuplicatedChoreException("The given chore already exists.");
            }
        }

        Chore chore = new Chore();
        chore.setDescription(description);
        chore.setDeadline(deadline);
        chore.setIsCompleted(Boolean.FALSE);

//        Chore chore = Chore.builder()
//                .description(description)
//                .deadline(deadline)
//                .isCompleted(Boolean.FALSE)
//                .build();
//        Chore chore = new Chore(description,deadline,Boolean.FALSE);

        chores.add(chore);
        return chore;
    }

    /**
     * Method to delete a given chore.
     *
     * @param description The description of the chore
     * @param deadline The deadline of the chore
     */
    public void deleteChore(String description, LocalDate deadline){
        if (this.chores.isEmpty()){
            throw new EmptyChoreListException("Unable to remove a chore from an empty list");
        }
        boolean isChoreExist = this.chores.stream().anyMatch((chore -> chore.getDescription().equals(description)
                && chore.getDeadline().isEqual(deadline)));
        if(!isChoreExist) {
            throw new ChoreNotFoundException("The given chore does not exist.");
        }

        this.chores = this.chores.stream().filter(chore -> !chore.getDescription().equals(description)
                && !chore.getDeadline().isEqual(deadline)).collect(Collectors.toList());

    }
}
