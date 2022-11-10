package com.arbetsprov.demo;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.Route;

import com.vaadin.flow.component.textfield.TextField;


import java.io.IOException;

import java.util.List;

@Route("")
public class MainView extends VerticalLayout {

    public MainView() throws IOException {
        add(new H1("Population by region in year 2017, 2018 and 2019"));
        add(new Paragraph("Select ^ next to the year you want to sort the population"));
        Select<String> select = new Select<>();
        select.setLabel("Sort by");
        select.setItems("Most recent first", "Rating: high to low",
                "Rating: low to high", "Price: high to low",
                "Price: low to high");
        select.setValue("Most recent first");
        add(select);

        Grid<Landscape> grid = new Grid<>(Landscape.class, false);
        grid.addColumn(Landscape::getName).setHeader("Landscape");
        grid.addColumn(Landscape::getPopulation17).setHeader("Population 2017").setSortable(true);
        grid.addColumn(Landscape::getPopulation18).setHeader("Population 2018").setSortable(true);
        grid.addColumn(Landscape::getPopulation19).setHeader("Population 2019").setSortable(true);
        grid.addColumn(Landscape::getPercentage_change_17_19).setHeader("Percentage change from 2017 - 2019").setSortable(false);

        List<Landscape> landscapeList = SCBReader.getData();
        grid.setItems(landscapeList);
        add(grid);

    }



}
