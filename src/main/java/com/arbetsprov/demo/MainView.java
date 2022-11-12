package com.arbetsprov.demo;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import java.io.IOException;

import java.util.List;
/**
 * Generates the gui.
 * Uses SCBReader to get the landscape data to display
 */
@Route("")
@CssImport(
        value = "grid-style.css",
        themeFor = "vaadin-grid"
)
@CssImport(
        value = "main-style.css")
public class MainView extends VerticalLayout {

    public MainView() throws IOException {

        add(new H1("Population by Landscape in year 2017, 2018 and 2019"));
        Grid<Landscape> grid = new Grid<>(Landscape.class, false);
        grid.addColumn(Landscape::getName).setHeader("Landscape");
        grid.addColumn(Landscape::getPopulation17).setHeader("Population 2017").setSortable(true);
        grid.addColumn(Landscape::getPopulation18).setHeader("Population 2018").setSortable(true);
        grid.addColumn(Landscape::getPopulation19).setHeader("Population 2019").setSortable(true);
        grid.addColumn(Landscape::getPercentageChange17to19).setHeader("% change from 2017 - 2019").setSortable(false);
        grid.setClassName("styled");
        List<Landscape> landscapeList = SCBReader.getData();
        grid.setItems(landscapeList);
        grid.setWidth("100%");
        grid.setHeight("600px");
        add(grid);
    }

}
