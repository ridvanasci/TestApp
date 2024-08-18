package com.example.testapp;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@PageTitle("Games")
@Route(value = "games")
@RouteAlias(value = "")
public class GameView extends VerticalLayout {
    private final GameRepository gameRepository;
    private Grid<Game> grid;

    public GameView(GameRepository gameRepository) {
        this.gameRepository = gameRepository;

        initUI();
    }

    private void initUI() {
        setSizeFull();

        TextField searchField = new TextField("Search by name");
        Button searchButton = new Button("Search");

        grid = new Grid<>();
        grid.addColumn(Game::getName).setHeader("Name");

        searchButton.addClickListener(event -> {
            String searchTerm = searchField.getValue();
            grid.setItems(gameRepository.findByNameContains(searchTerm));
        });

        add(searchField, searchButton, grid);
        grid.setItems(gameRepository.findAll());
    }
}