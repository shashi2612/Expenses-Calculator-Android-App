# Expenses-Calculator-Android-App
An expense calculator to track all your expenses

User Interface:
In First Activity Layout, user has an option to create a sheet for a particular month of a particular year.
User also needs to input his income for that month.
Once a sheet is added, now user can perform edit and delete operations for that particular sheet. Upon selecting, user is taken to another activity.
A page displays user’s inserted income and the remaining balance for that particular month. It has an “Add” button which allows user to add/edit/delete an expense & displays all the added expense.
Once user click on “Add Expense”, a new activity is started asking user to input the product amount and Product Description.
It also displays a checklist for Regular. If unchecked, meaning a non-regular expense.
Once a Product is added, it will automatically redirect the user to previous page displaying added item in a list form.
Here user again has an option to modify or delete any listed item, anytime. One good feature here is, user can directly change item from Regular to Non Regular & Vice-versa.
Addition of new sheet displays Month, Year & Income making it easy to navigate between different sheets is one another good feature.


Methods:
In Expense.java
An Expense class with a parameterized constructor containing for initializing the amount, label, itemType.

In Month.java
A Month class with a constructor containing different data types to store details such as month, year, income & list of expenses.

In FormExpenseActivity.java & FormMonthActivity.java
getIntent().getSerializableExtra() to access Expense Object.
setOnClickListener() to enable touch selection
finish() to shut particular Activity.
Intent.putExtra() helps to switch between different activities.

In MainActivity.java
Adapter class generally use for Listview.
getCount() – to get size of the list
getItem() – to retrieve selected item for editing.
getItemId() – to retrieve selected item to perform delete operation.
startActivityForResult() – for new activity
database.remove() – for deletion
adapter.notifyDataSetChanged – Notifies the attached observers that the underlying data has been changed & any View reflecting the data set should refresh itself.
saveDatabase() – to save changes.
loadDatabase() – to load Database

File to store values. Followings methods are all based on that.
FileInputStream() – load a file.
New File() – create a new text file.
getFilesDir() – to access to file.
close() – to close file

@Shashi2612
