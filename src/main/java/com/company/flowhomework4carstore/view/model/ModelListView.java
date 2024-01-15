package com.company.flowhomework4carstore.view.model;

import com.company.flowhomework4carstore.entity.Model;
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

@Route(value = "models", layout = MainView.class)
@ViewController("Model.list")
@ViewDescriptor("model-list-view.xml")
@LookupComponent("modelsDataGrid")
@DialogMode(width = "64em")
public class ModelListView extends StandardListView<Model> {

    @ViewComponent
    private DataContext dataContext;

    @ViewComponent
    private CollectionContainer<Model> modelsDc;

    @ViewComponent
    private InstanceContainer<Model> modelDc;

    @ViewComponent
    private InstanceLoader<Model> modelDl;

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

    @Subscribe("modelsDataGrid.create")
    public void onModelsDataGridCreate(final ActionPerformedEvent event) {
        dataContext.clear();
        Model entity = dataContext.create(Model.class);
        modelDc.setItem(entity);
        updateControls(true);
    }

    @Subscribe("modelsDataGrid.edit")
    public void onModelsDataGridEdit(final ActionPerformedEvent event) {
        updateControls(true);
    }

    @Subscribe("saveBtn")
    public void onSaveButtonClick(final ClickEvent<JmixButton> event) {
        dataContext.save();
        modelsDc.replaceItem(modelDc.getItem());
        updateControls(false);
    }

    @Subscribe("cancelBtn")
    public void onCancelButtonClick(final ClickEvent<JmixButton> event) {
        dataContext.clear();
        modelDl.load();
        updateControls(false);
    }

    @Subscribe(id = "modelsDc", target = Target.DATA_CONTAINER)
    public void onModelsDcItemChange(final InstanceContainer.ItemChangeEvent<Model> event) {
        Model entity = event.getItem();
        dataContext.clear();
        if (entity != null) {
            modelDl.setEntityId(entity.getId());
            modelDl.load();
        } else {
            modelDl.setEntityId(null);
            modelDc.setItem(null);
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