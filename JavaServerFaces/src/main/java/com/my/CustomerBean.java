package com.my;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@ManagedBean(name = "customer")
@SessionScoped
public class CustomerBean implements Serializable {

	private static final long serialVersionUID = 1L;

	// direction of sorting
	private boolean sortAscending = true;

	// temp main list of drugs
	private List<Customer> list_temp;

	// list what show to user
	private List<Customer> list;

	// list what we use in cart
	private List<Customer> list2;

	// list for each item name of drug
	private List<Customer> list3;

	// list4 for total counter sum
	private List<Customer> list4;

	// list5 for create unique Form list
	private List<Customer> list5;

	// list7 for create unique Id list
	private List<Customer> list7;

	// list8 for create unique Name list
	private List<Customer> list8;

	// list9 for create unique Group list
	private List<Customer> list9;

	// list10 for create unique Manufacturer list
	private List<Customer> list10;

	// list11 for create unique Price list
	private List<Customer> list11;

	// temp list then after filtering add to main list
	// use if user filtering
	private List<Customer> list6;

	// variables for the construction of drugs from the database "medicine"
	public String ID;
	public String Name;
	public String Group;
	public String Manufacturer;
	public String Form;
	public String Price;
	public String Quantity;

	// amount for the one name of item
	public String Total;
	// amount for all buy
	public String TotalSum;

	// filter what choose user
	public String form_filter;
	public String id_filter;
	public String name_filter;
	public String group_filter;
	public String manufacturer_filter;
	public String price_filter;

	// stores name of drag where we must go
	public String page;

	@Resource(name = "jdbc/medicine")
	private DataSource ds;

	public CustomerBean() throws SQLException {
		try {

			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/medicine");

			if (ds == null)
				throw new SQLException("Can't get data source");

			// get database connection
			Connection con = ds.getConnection();

			if (con == null)
				throw new SQLException("Can't get database connection");

			PreparedStatement ps = con
					.prepareStatement("select * from customer");

			// get customer data from database
			ResultSet result = ps.executeQuery();

			list_temp = new ArrayList<Customer>();
			list = new ArrayList<Customer>();
			list2 = new ArrayList<Customer>();
			list3 = new ArrayList<Customer>();
			list4 = new ArrayList<Customer>();
			list5 = new ArrayList<Customer>();
			list6 = new ArrayList<Customer>();
			list7 = new ArrayList<Customer>();
			list8 = new ArrayList<Customer>();
			list9 = new ArrayList<Customer>();
			list10 = new ArrayList<Customer>();
			list11 = new ArrayList<Customer>();

			while (result.next()) {
				Customer cust = new Customer();

				cust.setID(result.getString("ID"));
				cust.setName(result.getString("NAME"));
				cust.setGroup(result.getString("GROUP"));
				cust.setManufacturer(result.getString("MANUFACTURER"));
				cust.setForm(result.getString("FORM"));
				cust.setPrice(result.getString("PRICE"));
				cust.setQuantity(result.getString("QUANTITY"));

				// store all data into a list's
				list_temp.add(cust);
				list.add(cust);
				list5.add(cust);
				list6.add(cust);
				list7.add(cust);
				list8.add(cust);
				list9.add(cust);
				list10.add(cust);
				list11.add(cust);

				// filtered lists for each of its value
				filteringFormList(list5);
				filteringIdList(list7);
				filteringNameList(list8);
				filteringGroupList(list9);
				filteringManufacturerList(list10);
				filteringPriceList(list11);

			}

			// create a blank filter to remove filtering
			Customer cust2 = new Customer();

			cust2.setID(" ");
			cust2.setName(" ");
			cust2.setGroup(" ");
			cust2.setManufacturer(" ");
			cust2.setForm(" ");
			cust2.setPrice(" ");
			cust2.setQuantity(" ");

			list5.add(cust2);
			list7.add(cust2);
			list8.add(cust2);
			list9.add(cust2);
			list10.add(cust2);
			list11.add(cust2);

			// add empty value to each list's
			addEmptyTofilterList(list5);
			addEmptyTofilterList(list7);
			addEmptyTofilterList(list8);
			addEmptyTofilterList(list9);
			addEmptyTofilterList(list10);
			addEmptyTofilterList(list11);

			con.close();

			// get all existing value but set "editable" to true
			// can edit quantity when page load
			for (Customer customer : list) {
				customer.setEditable(true);
			}

		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	// add empty value to list and return it
	public List<Customer> addEmptyTofilterList(List<Customer> list53) {

		for (int i = list53.size() - 1; i > 0; i--) {
			Customer cust3 = list53.get(i);
			Customer cust4 = list53.get(i - 1);
			list53.set(i, cust4);
			list53.set(i - 1, cust3);
		}

		return list53;
	}

	// filter input list from repetitive word's in Form
	public List<Customer> filteringFormList(List<Customer> list52) {
		for (int i = 0; i < list52.size() - 1; i++) {

			for (int j = i + 1; j < list52.size(); j++) {

				if (list52.get(i).getForm().toString()
						.equals(list.get(j).getForm().toString())) {
					// System.out.print("REMOVE! ");
					list52.remove(j);
				}

			}
		}

		return list52;
	}

	// filter input list from repetitive word's in ID
	public List<Customer> filteringIdList(List<Customer> list52) {
		for (int i = 0; i < list52.size() - 1; i++) {

			for (int j = i + 1; j < list52.size(); j++) {

				if (list52.get(i).getID().toString()
						.equals(list.get(j).getID().toString())) {
					list52.remove(j);
				}

			}
		}

		return list52;
	}

	// filter input list from repetitive word's in Name
	public List<Customer> filteringNameList(List<Customer> list52) {
		for (int i = 0; i < list52.size() - 1; i++) {

			for (int j = i + 1; j < list52.size(); j++) {

				if (list52.get(i).getName().toString()
						.equals(list.get(j).getName().toString())) {
					list52.remove(j);
				}

			}
		}

		return list52;
	}

	// filter input list from repetitive word's in Group
	public List<Customer> filteringGroupList(List<Customer> list52) {
		for (int i = 0; i < list52.size() - 1; i++) {

			for (int j = i + 1; j < list52.size(); j++) {

				if (list52.get(i).getGroup().toString()
						.equals(list.get(j).getGroup().toString())) {
					list52.remove(j);
				}

			}
		}

		return list52;
	}

	// filter input list from repetitive word's in Manufacturer
	public List<Customer> filteringManufacturerList(List<Customer> list52) {
		for (int i = 0; i < list52.size() - 1; i++) {

			for (int j = i + 1; j < list52.size(); j++) {

				if (list52.get(i).getManufacturer().toString()
						.equals(list.get(j).getManufacturer().toString())) {
					list52.remove(j);
				}

			}
		}

		return list52;
	}

	// filter input list from repetitive word's in Price
	public List<Customer> filteringPriceList(List<Customer> list52) {
		for (int i = 0; i < list52.size() - 1; i++) {

			for (int j = i + 1; j < list52.size(); j++) {

				if (list52.get(i).getPrice().toString()
						.equals(list.get(j).getPrice().toString())) {
					list52.remove(j);
				}

			}
		}

		return list52;
	}

	// sorting

	// sort by ID
	public String sortByOrderNo() {

		if (sortAscending) {

			// ascending order
			Collections.sort(list, new Comparator<Customer>() {

				@Override
				public int compare(Customer o1, Customer o2) {

					return o1.getID().compareTo(o2.getID());

				}

			});
			sortAscending = false;

		} else {

			// descending order
			Collections.sort(list, new Comparator<Customer>() {

				@Override
				public int compare(Customer o1, Customer o2) {

					return o2.getID().compareTo(o1.getID());

				}

			});
			sortAscending = true;
		}

		return null;
	}

	// sort by Name

	public String sortByName() {

		if (sortAscending) {

			// ascending order
			Collections.sort(list, new Comparator<Customer>() {

				@Override
				public int compare(Customer o1, Customer o2) {

					return o1.getName().compareTo(o2.getName());

				}

			});
			sortAscending = false;

		} else {

			// descending order
			Collections.sort(list, new Comparator<Customer>() {

				@Override
				public int compare(Customer o1, Customer o2) {

					return o2.getName().compareTo(o1.getName());

				}

			});
			sortAscending = true;
		}

		return null;
	}

	// sort by Group

	public String sortByGroup() {

		if (sortAscending) {

			// ascending order
			Collections.sort(list, new Comparator<Customer>() {

				@Override
				public int compare(Customer o1, Customer o2) {

					return o1.getGroup().compareTo(o2.getGroup());

				}

			});
			sortAscending = false;

		} else {

			// descending order
			Collections.sort(list, new Comparator<Customer>() {

				@Override
				public int compare(Customer o1, Customer o2) {

					return o2.getGroup().compareTo(o1.getGroup());

				}

			});
			sortAscending = true;
		}

		return null;
	}

	// sort by Manufacturer

	public String sortByManufacturer() {

		if (sortAscending) {

			// ascending order
			Collections.sort(list, new Comparator<Customer>() {

				@Override
				public int compare(Customer o1, Customer o2) {

					return o1.getManufacturer().compareTo(o2.getManufacturer());

				}

			});
			sortAscending = false;

		} else {

			// descending order
			Collections.sort(list, new Comparator<Customer>() {

				@Override
				public int compare(Customer o1, Customer o2) {

					return o2.getManufacturer().compareTo(o1.getManufacturer());

				}

			});
			sortAscending = true;
		}

		return null;
	}

	// sort by Form

	public String sortByForm() {

		if (sortAscending) {

			// ascending order
			Collections.sort(list, new Comparator<Customer>() {

				@Override
				public int compare(Customer o1, Customer o2) {

					return o1.getForm().compareTo(o2.getForm());

				}

			});
			sortAscending = false;

		} else {

			// descending order
			Collections.sort(list, new Comparator<Customer>() {

				@Override
				public int compare(Customer o1, Customer o2) {

					return o2.getForm().compareTo(o1.getForm());

				}

			});
			sortAscending = true;
		}

		return null;
	}

	// sort by Price

	public String sortByPrice() {

		if (sortAscending) {

			// ascending order
			Collections.sort(list, new Comparator<Customer>() {

				@Override
				public int compare(Customer o1, Customer o2) {

					return o1.getPrice().compareTo(o2.getPrice());

				}

			});
			sortAscending = false;

		} else {

			// descending order
			Collections.sort(list, new Comparator<Customer>() {

				@Override
				public int compare(Customer o1, Customer o2) {

					return o2.getPrice().compareTo(o1.getPrice());

				}

			});
			sortAscending = true;
		}

		return null;
	}

	// sort by Quantity

	public String sortByQuantity() {

		if (sortAscending) {

			// ascending order
			Collections.sort(list, new Comparator<Customer>() {

				@Override
				public int compare(Customer o1, Customer o2) {

					return o1.getQuantity().compareTo(o2.getQuantity());

				}

			});
			sortAscending = false;

		} else {

			// descending order
			Collections.sort(list, new Comparator<Customer>() {

				@Override
				public int compare(Customer o1, Customer o2) {

					return o2.getQuantity().compareTo(o1.getQuantity());

				}

			});
			sortAscending = true;
		}

		return null;
	}

	// go to "cart" page
	public String outcome() {

		for (Customer customer : list2) {
			customer.setEditable(true);
		}

		return "cart";
	}

	// go to "page" of drug item page
	public String outcome2() {

		return page;
	}

	// go to "default" page
	public String outcome3() {

		return "default";
	}

	// go to "checkout" page
	public String checkout() {

		return "checkout";

	}

	// action listener event for create cart list
	public void attrListener(ActionEvent event) {

		ID = (String) event.getComponent().getAttributes().get("ID");
		Name = (String) event.getComponent().getAttributes().get("name");
		Group = (String) event.getComponent().getAttributes().get("group");
		Manufacturer = (String) event.getComponent().getAttributes()
				.get("manufacturer");
		Form = (String) event.getComponent().getAttributes().get("form");
		Price = (String) event.getComponent().getAttributes().get("price");
		Quantity = (String) event.getComponent().getAttributes()
				.get("quantity");

		Customer cust = new Customer();
		cust.setID(ID);
		cust.setName(Name);
		cust.setGroup(Group);
		cust.setManufacturer(Manufacturer);
		cust.setForm(Form);
		cust.setPrice(Price);
		cust.setQuantity(Quantity);

		if (!list2.isEmpty()) {

			int flag = 0;
			for (int i = 0; i < list2.size(); i++) {
				if (list2.get(i).getID() == cust.getID()) {
					list2.get(i).setQuantity(
							Integer.toString(Integer.parseInt(list2.get(i)
									.getQuantity())
									+ Integer.parseInt(Quantity)));
					flag++;
				}
			}
			if (flag == 0) {
				list2.add(cust);
			}
		} else {
			list2.add(cust);
		}

		for (Customer customer : list2) {
			customer.setEditable(true);
		}

	}

	// action listener event for create list of one item in drug item page
	public void attrListener2(ActionEvent event) {

		ID = (String) event.getComponent().getAttributes().get("ID");
		Name = (String) event.getComponent().getAttributes().get("name");
		page = Name;
		Group = (String) event.getComponent().getAttributes().get("group");
		Manufacturer = (String) event.getComponent().getAttributes()
				.get("manufacturer");
		Form = (String) event.getComponent().getAttributes().get("form");
		Price = (String) event.getComponent().getAttributes().get("price");
		Quantity = (String) event.getComponent().getAttributes()
				.get("quantity");

		Customer cust = new Customer();
		cust.setID(ID);
		cust.setName(Name);
		cust.setGroup(Group);
		cust.setManufacturer(Manufacturer);
		cust.setForm(Form);
		cust.setPrice(Price);
		cust.setQuantity(Quantity);

		list3.clear();

		if (list3.isEmpty()) {
			list3.add(cust);
		}

		for (Customer customer : list3) {
			customer.setEditable(true);
		}

	}

	// action listener event for create list where count money in "checkout"
	// page
	public void attrListener3(ActionEvent event) {

		for (Customer customer : list2) {
			customer.setEditable(false);
		}

		ID = (String) event.getComponent().getAttributes().get("ID");
		Name = (String) event.getComponent().getAttributes().get("name");
		Group = (String) event.getComponent().getAttributes().get("group");
		Manufacturer = (String) event.getComponent().getAttributes()
				.get("manufacturer");
		Form = (String) event.getComponent().getAttributes().get("form");
		Price = (String) event.getComponent().getAttributes().get("price");
		Quantity = (String) event.getComponent().getAttributes()
				.get("quantity");

		Customer cust = new Customer();
		cust.setID(ID);
		cust.setName(Name);
		cust.setGroup(Group);
		cust.setManufacturer(Manufacturer);
		cust.setForm(Form);
		cust.setPrice(Price);
		cust.setQuantity(Quantity);

		for (int i = 0; i < list2.size(); i++) {
			list2.get(i).setTotal(
					Double.toString(Integer
							.parseInt(list2.get(i).getQuantity())
							* Double.parseDouble(list2.get(i).getPrice())));
		}

		// for count total sum
		Double sum = 0.0;
		for (int i = 0; i < list2.size(); i++) {
			sum += Double.parseDouble(list2.get(i).getTotal());
		}

		cust.setTotalSum(sum.toString());
		list4.clear();
		list4.add(cust);

	}

	// action listener event for get filter
	public void attrListener4(ActionEvent event) {

		ID = (String) event.getComponent().getAttributes().get("ID");
		Form = (String) event.getComponent().getAttributes().get("form");
		Name = (String) event.getComponent().getAttributes().get("name");
		Group = (String) event.getComponent().getAttributes().get("group");
		Manufacturer = (String) event.getComponent().getAttributes()
				.get("manufacturer");
		Price = (String) event.getComponent().getAttributes().get("price");

		form_filter = Form;
		id_filter = ID;
		name_filter = Name;
		group_filter = Group;
		manufacturer_filter = Manufacturer;
		price_filter = Price;

	}

	// delete item in cart list
	public String deleteAction(Customer customer) {

		list2.remove(customer);
		return null;
	}

	// for set filter in Form list
	public String actionFilter() {

		// filter for checking form
		if (list5.get(0).getForm().toString().equals(form_filter)) {
			list.clear();
			list6.clear();
			list.addAll(list_temp);
			list6.addAll(list_temp);
		} else {
			for (int j = 0; j < list_temp.size(); j++) {
				for (int i = 0; i < list6.size(); i++) {
					if (list6.get(i).getForm().toString().equals(form_filter)) {
					} else {
						list6.remove(i);
					}
				}
			}

			list.clear();
			list.addAll(list6);
		}

		return "default";
	}

	// for set filter in ID list
	public String actionFilter2() {

		// filter for checking id
		if (list7.get(0).getID().toString().equals(id_filter)) {
			list.clear();
			list6.clear();
			list.addAll(list_temp);
			list6.addAll(list_temp);
		} else {
			for (int j = 0; j < list_temp.size(); j++) {
				for (int i = 0; i < list6.size(); i++) {
					if (list6.get(i).getID().toString().equals(id_filter)) {
					} else {
						list6.remove(i);
					}
				}
			}
			list.clear();
			list.addAll(list6);
		}

		return "default";
	}

	// for set filter in Name list
	public String actionFilter3() {

		// filter for checking Name
		if (list8.get(0).getName().toString().equals(name_filter)) {
			list.clear();
			list6.clear();
			list.addAll(list_temp);
			list6.addAll(list_temp);
		} else {
			for (int j = 0; j < list_temp.size(); j++) {
				for (int i = 0; i < list6.size(); i++) {
					if (list6.get(i).getName().toString().equals(name_filter)) {
					} else {
						list6.remove(i);
					}
				}
			}
			list.clear();
			list.addAll(list6);
		}

		return "default";
	}

	// for set filter in Group list
	public String actionFilter4() {

		// filter for checking group
		if (list9.get(0).getGroup().toString().equals(group_filter)) {
			list.clear();
			list6.clear();
			list.addAll(list_temp);
			list6.addAll(list_temp);
		} else {
			for (int j = 0; j < list_temp.size(); j++) {
				for (int i = 0; i < list6.size(); i++) {
					if (list6.get(i).getGroup().toString().equals(group_filter)) {
					} else {
						list6.remove(i);
					}
				}
			}
			list.clear();
			list.addAll(list6);
		}

		return "default";
	}

	// for set filter in Manufacrurer list
	public String actionFilter5() {

		// filter for checking manufacturer
		if (list10.get(0).getManufacturer().toString()
				.equals(manufacturer_filter)) {
			list.clear();
			list6.clear();
			list.addAll(list_temp);
			list6.addAll(list_temp);
		} else {
			for (int j = 0; j < list_temp.size(); j++) {
				for (int i = 0; i < list6.size(); i++) {
					if (list6.get(i).getManufacturer().toString()
							.equals(manufacturer_filter)) {
					} else {
						list6.remove(i);
					}
				}
			}
			list.clear();
			list.addAll(list6);
		}

		return "default";
	}

	// for set filter in Price list
	public String actionFilter6() {

		// filter for checking price
		if (list11.get(0).getPrice().toString().equals(price_filter)) {
			list.clear();
			list6.clear();
			list.addAll(list_temp);
			list6.addAll(list_temp);
		} else {
			for (int j = 0; j < list_temp.size(); j++) {
				for (int i = 0; i < list6.size(); i++) {
					if (list6.get(i).getPrice().toString().equals(price_filter)) {
					} else {
						list6.remove(i);
					}
				}
			}
			list.clear();
			list.addAll(list6);
		}

		return "default";
	}

	public String saveAction() {

		// get all existing value but set "editable" to false
		for (Customer customer : list) {
			customer.setEditable(false);
		}
		// return to current page
		return null;
	}

	public String editAction(Customer customer) {

		customer.setEditable(true);
		return null;
	}

	// get list
	public List<Customer> getList() throws SQLException {

		return list;
	}

	public List<Customer> getList2() throws SQLException {

		return list2;
	}

	public List<Customer> getList3() throws SQLException {

		return list3;
	}

	public List<Customer> getList4() throws SQLException {

		return list4;
	}

	public List<Customer> getList5() throws SQLException {

		return list5;
	}

	public List<Customer> getList7() throws SQLException {

		return list7;
	}

	public List<Customer> getList8() throws SQLException {

		return list8;
	}

	public List<Customer> getList9() throws SQLException {

		return list9;
	}

	public List<Customer> getList10() throws SQLException {

		return list10;
	}

	public List<Customer> getList11() throws SQLException {

		return list11;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public String getID() {
		return ID;
	}

	public void setName(String Name) {
		this.Name = Name;
	}

	public String getName() {
		return Name;
	}

	public void setGroup(String Group) {
		this.Group = Group;
	}

	public String getGroup() {
		return Group;
	}

	public void setManufacturer(String Manufacturer) {
		this.Manufacturer = Manufacturer;
	}

	public String getManufacturer() {
		return Manufacturer;
	}

	public void setForm(String Form) {
		this.Form = Form;
	}

	public String getForm() {
		return Form;
	}

	public void setPrice(String Price) {
		this.Price = Price;
	}

	public String getPrice() {
		return Price;
	}

	public void setQuantity(String Quantity) {
		this.Quantity = Quantity;
	}

	public String getQuantity() {
		return Quantity;
	}

	public void setTotal(String Total) {
		this.Total = Total;
	}

	public String getTotal() {
		return Total;
	}

	public void setTotalSum(String TotalSum) {
		this.TotalSum = TotalSum;
	}

	public String getTotalSum() {
		return TotalSum;
	}

	public void setForm_filter(String form_filter) {
		this.form_filter = form_filter;
	}

	public String getForm_filter() {
		return form_filter;
	}

	public void setId_filter(String id_filter) {
		this.id_filter = id_filter;
	}

	public String getId_filter() {
		return id_filter;
	}

	public void setName_filter(String name_filter) {
		this.name_filter = name_filter;
	}

	public String getName_filter() {
		return name_filter;
	}

	public void setGroup_filter(String group_filter) {
		this.group_filter = group_filter;
	}

	public String getGroup_filter() {
		return group_filter;
	}

	public void setManufacturer_filter(String manufacturer_filter) {
		this.manufacturer_filter = manufacturer_filter;
	}

	public String getManufacturer_filter() {
		return manufacturer_filter;
	}

	public void setPrice_filter(String price_filter) {
		this.price_filter = price_filter;
	}

	public String getPrice_filter() {
		return price_filter;
	}

	public class Customer {

		public String ID;
		public String Name;
		public String Group;
		public String Manufacturer;
		public String Form;
		public String Price;
		public String Quantity;
		boolean editable;

		public String Total;
		public String TotalSum;

		public Customer(String ID, String Name, String Group,
				String Manufacturer, String Form, String Price, String Quantity) {
			this.ID = ID;
			this.Name = Name;
			this.Group = Group;
			this.Manufacturer = Manufacturer;
			this.Form = Form;
			this.Price = Price;
			this.Quantity = Quantity;
		}

		public Customer() {
		}

		public Customer(String Name) {
			this.Name = Name;
		}

		public boolean isEditable() {
			return editable;
		}

		public void setEditable(boolean editable) {
			this.editable = editable;
		}

		public void setID(String ID) {
			this.ID = ID;
		}

		public String getID() {
			return ID;
		}

		public void setName(String Name) {
			this.Name = Name;
		}

		public String getName() {
			return Name;
		}

		public void setGroup(String Group) {
			this.Group = Group;
		}

		public String getGroup() {
			return Group;
		}

		public void setManufacturer(String Manufacturer) {
			this.Manufacturer = Manufacturer;
		}

		public String getManufacturer() {
			return Manufacturer;
		}

		public void setForm(String Form) {
			this.Form = Form;
		}

		public String getForm() {
			return Form;
		}

		public void setPrice(String Price) {
			this.Price = Price;
		}

		public String getPrice() {
			return Price;
		}

		public void setQuantity(String Quantity) {
			this.Quantity = Quantity;
		}

		public String getQuantity() {
			return Quantity;
		}

		public void setTotal(String Total) {
			this.Total = Total;
		}

		public String getTotal() {
			return Total;
		}

		public void setTotalSum(String TotalSum) {
			this.TotalSum = TotalSum;
		}

		public String getTotalSum() {
			return TotalSum;
		}

	}

}
