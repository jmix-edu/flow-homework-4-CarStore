package com.company.flowhomework4carstore.view.manufacturer;

import com.company.flowhomework4carstore.entity.Manufacturer;
import com.company.flowhomework4carstore.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.*;
import io.jmix.flowui.view.*;

@Route(value = "manufacturers", layout = MainView.class)
@ViewController("Manufacturer.list")
@ViewDescriptor("manufacturer-list-view.xml")
@LookupComponent("manufacturersDataGrid")
@DialogMode(width = "64em")
public class ManufacturerListView extends StandardListView<Manufacturer> {

    @ViewComponent
    private DataContext dataContext;

    @ViewComponent
    private CollectionContainer<Manufacturer> manufacturersDc;

    @ViewComponent
    private InstanceContainer<Manufacturer> manufacturerDc;

    @ViewComponent
    private InstanceLoader<Manufacturer> manufacturerDl;

    @ViewComponent
    private VerticalLayout listLayout;

    @ViewComponent
    private FormLayout form;

    @ViewComponent
    private HorizontalLayout detailActions;

    @Subscribe
    public void onInit(final InitEvent event) {
        updateControls(false);
    }

    @Subscribe("manufacturersDataGrid.create")
    public void onManufacturersDataGridCreate(final ActionPerformedEvent event) {
        dataContext.clear();
        Manufacturer entity = dataContext.create(Manufacturer.class);
        manufacturerDc.setItem(entity);
        updateControls(true);
    }

    @Subscribe("manufacturersDataGrid.edit")
    public void onManufacturersDataGridEdit(final ActionPerformedEvent event) {
        updateControls(true);
    }

    @Subscribe("saveBtn")
    public void onSaveButtonClick(final ClickEvent<JmixButton> event) {
        dataContext.save();
        manufacturersDc.replaceItem(manufacturerDc.getItem());
        updateControls(false);
    }

    @Subscribe("cancelBtn")
    public void onCancelButtonClick(final ClickEvent<JmixButton> event) {
        dataContext.clear();
        manufacturerDl.load();
        updateControls(false);
    }

    @Subscribe(id = "manufacturersDc", target = Target.DATA_CONTAINER)
    public void onManufacturersDcItemChange(final InstanceContainer.ItemChangeEvent<Manufacturer> event) {
        Manufacturer entity = event.getItem();
        dataContext.clear();
        if (entity != null) {
            manufacturerDl.setEntityId(entity.getId());
            manufacturerDl.load();
        } else {
            manufacturerDl.setEntityId(null);
            manufacturerDc.setItem(null);
        }
    }

    private void updateControls(boolean editing) {
        form.getChildren().forEach(component -> {
            if (component instanceof HasValueAndElement<?, ?> field) {
                field.setReadOnly(!editing);
            }
        });

        detailActions.setVisible(editing);
        listLayout.setEnabled(!editing);
    }
}