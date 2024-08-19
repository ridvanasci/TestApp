package com.example.testapp;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@PageTitle("Game")
@Route(value = "game")
@RouteAlias(value = "")
public class GameView extends VerticalLayout {
    private final  GameRepository gameRepository;
    private Grid<Game> grid;

    public GameView(GameRepository gameRepository) {
        this.gameRepository = gameRepository;

        initUI();
    }

    private void initUI() {
        setSizeFull();

        TextField searchField = new TextField("Search by name");
        Button searchButton = new Button("Search");
        Button addButton = new Button("Add");
        addButton.addClickListener(event -> {
            TextField nameField = new TextField("Name");
            TextField descriptionField = new TextField("Description");
            Button saveButton = new Button("Save");
            VerticalLayout layout = new VerticalLayout(nameField, descriptionField, saveButton);
            add(layout);

            saveButton.addClickListener(e -> {
                Game newGame = new Game();
                newGame.setName(nameField.getValue());
                newGame.setDescription(descriptionField.getValue());
                gameRepository.save(newGame);
                grid.setItems(gameRepository.findAll());
                remove(layout);
            });
        });

        Button editButton = new Button("Edit");
        Button deleteButton = new Button("Delete");

        grid = new Grid<>();
        grid.addColumn(Game::getName).setHeader("Name");
        grid.addColumn(Game::getDescription).setHeader("Description");
        grid.addColumn(Game::getId).setHeader("Id");

        searchButton.addClickListener(event -> {
            String searchTerm = searchField.getValue();
            grid.setItems(gameRepository.findByNameContains(searchTerm));
        });

        editButton.addClickListener(event -> {
            Game selectedGame = grid.getSelectedItems().iterator().next();
            if (selectedGame != null) {
                TextField nameField = new TextField("Name");
                TextField descriptionField = new TextField("Description");
                nameField.setValue(selectedGame.getName());
                descriptionField.setValue(selectedGame.getDescription());
                Button saveButton = new Button("Save");
                saveButton.addClickListener(e -> {
                    selectedGame.setName(nameField.getValue());
                    selectedGame.setDescription(descriptionField.getValue());
                    gameRepository.save(selectedGame);
                    grid.setItems(gameRepository.findAll());
                });
                add(nameField, descriptionField, saveButton);
            }
        });

        deleteButton.addClickListener(event -> {
            Game selectedGame = grid.getSelectedItems().iterator().next();
            if (selectedGame != null) {
                gameRepository.delete(selectedGame);
                grid.setItems(gameRepository.findAll());
            }
        });

        add(searchField, searchButton, addButton, editButton, deleteButton, grid);
        grid.setItems(gameRepository.findAll());
    }
}