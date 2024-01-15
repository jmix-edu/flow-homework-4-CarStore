### Prepared project
This project contains some elements from "used cars sailing" area:
- Manufacturer and Model entities, and EngineType enumeration
- Views for listing and editing for Manufacturer and Model (Master-detail view template based)

### Task
In the exercise you need to expand data model with a new entity and add some logic for data reading and manipulation.

#### 1. Adding Enumeration and new Entity
Create enumeration class for setting the Car statuses:
- CarStatus - Enumeration. Select from:
    - In Stock (I) , or 
    - Sold (S).

Using Studio, and it's Entity designer add new entity - Car with attributes listed below:
- registrationNumber (registration number, String). Mandatory.
    - Validation: exactly 6 symbols in length.
    - Use designer annotations to add the validation.
- model (car model, associated with Model entity). Mandatory.
    - check suitable relation type between car and model.
- productionYear (car production year, integer)
    - Validation: from 1990 to 2030 inclusively.
- status - use enum created above. Think about making the field mandatory or init it in advance with default value.
- dateOfSale (sailing date). Date without time.
    - Chose suitable datatype.

Also:
- Set car instance name using registrationNumber attribute.
- Create index to control the car's registrationNumber uniqueness.

#### 2. Car Views
- Create standard list and editor(detail) views for Car entities.
- Launch the application.
- Check if registration number and production year validations work as expected (unique registration number too).
- Create 5 or more Car instances in working application as demo data for further development,
#### OR - optionally:
- Work with prepared XML for demo data generation:
    - Copy and run additional migration script. Use the demo-data-cars.xml file from resources directory. To do so, create a new Liquibase Changelog file using studio and add changeset from demo-data-cars.xml to generated file. Remember to restart the application to trigger data generation.

#### 3. Read data with DataManager
Add to Manufacturers list view new action for manufacturer's cars calculation as below:
- Action caption - "Calculate cars"
- When button is pressed, system should count number of cars of selected in table manufacturer, with separation by Model#engineType.
- As mentioned above, gasoline and electric cars must be counted separately.
- After counting is done, show notification with text:
  "Electric cars: 3, gasoline cars: 5" (text formatting is not strict)

Recommendations:
- Add new button to the Manufacturer List View buttons panel.
- Create a Spring bean for logic mentioned above.
- Realise counting logic using DataManager and JPQL query.

#### 4. DataManager data manipulation
Realise in Cars view new action for rapid car status change -  from In Stock to Sold.
Logic implementation should be like below:
- Action caption "Mark as Sold"
- If selected car status is already "Sold" - show notification: "Already Sold".
- If selected car status is "In Stock" - then:
    - Change status to Sold.
    - Set date of sale field as "today".
    - Save the changes.
    - Show notification: "Done".

Rethink in this place, what could go wrong if car's status is not initialized when car is created?

Recommendations:
- Add new button to existed buttons panel.
- Use DataManager bean for changed entity saving.