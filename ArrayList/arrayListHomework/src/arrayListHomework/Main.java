package arrayListHomework;

import java.util.ArrayList;
import java.util.Collections;

public class Main {

	public static void main(String[] args) {
		ArrayList<String> colors = new ArrayList<>();

		// Add a new element in the ArrayList
		colors.add("Blue");
		colors.add("Green");
		colors.add("Pink");
		colors.add("Red");
		colors.add("Pink");
		colors.add("Orange");
		System.out.println(colors);

		// Add new element to certain index in the ArrayList
		colors.add(0, "Gray");
		System.out.println(colors);

		// Checks for element in given value in list the ArrayList
		if (colors.contains("Red")) {
			System.out.println("This element is in the ArrayList");
		} else {
			System.out.println("This element is not in the ArrayList");
		}

		// Sort the ArrayList of String type alphabetically
		Collections.sort(colors);

		System.out.println("ArrayList after sorting operation");
		// Iterating ArrayList
		for (String str : colors) {
			System.out.println(str);
		}

		// Returns index of given element (returns -1 if this element does not exist)
		System.out.println(colors.indexOf("Blue"));

		// Returns the index of the last occurrence of the specified element in this list
		System.out.println(colors.lastIndexOf("Pink"));

		// Get an element from the ArrayList with index
		System.out.println("Element in index 0: " + colors.get(0));

		// Change an element from ArrayList with index and new value
		colors.set(5, "Yellow");
		System.out.println("Element in index 5: " + colors.get(5));

		// Creates a subList among the specified indexes and returns this list.
		System.out.println("Sublist: " + colors.subList(1, 5));

		// Remove an element from the Arraylist with index
		colors.remove(2);
		// Remove an element from the Arraylist with element
		colors.remove("Yellow");

		System.out.println("Arraylist after remove operations: " + colors);
		System.out.println("Size of the ArrayList is : " + colors.size());

		ArrayList<String> newColors = new ArrayList<>();
		newColors.add("Gray");
		newColors.add("Red");
		// Return a shallow copy of an ArrayList.
		System.out.println("Clone of newColors: " + newColors.clone());

		// Removes from the Arraylist all of its elements that are contained in the specified collection.
		colors.removeAll(newColors);
		System.out.println("Arraylist after remove operation: " + colors);

		// Adds to the Arraylist all of its elements that are contained in the specified collection.
		colors.addAll(newColors);
		System.out.println("Arraylist after add operation: " + colors);

		// Remove all elemnets in the Arraylist
		colors.clear();
		System.out.println(colors);

		// Returns true if this list contains no elements.
		System.out.println(colors.isEmpty());

		// Generic ArrayList
		System.out.println("\nGeneric ArrayList");
		System.out.println("-------------------");
		ArrayList genericArrayList = new ArrayList<>();
		genericArrayList.add("Eda");
		genericArrayList.add(22);
		genericArrayList.add("Computer Science");
		genericArrayList.add(1999);

		System.out.println(genericArrayList);

	}

}
