/*
 * Write a method to build a tree from the right parenthetic string representation (return the root node of the tree)
 * and a method to construct the left parenthetic string representation of a tree represented by the root node this.
 * Tree is a pointer structure with two pointers to link nodes of type Node - pointer to the first child and pointer to
 * the next sibling. Build test trees and print the results in main-method, do not forget to test a tree that consists
 * of one node only. Node name must be non-empty and must not contain round brackets, commas and whitespace symbols. In
 * case of an invalid input string the parsePostfix method must throw a RuntimeException with meaningful error message.
 *
 * Used materials:
 * http://enos.itcollege.ee/~jpoial/algoritmid/puud.html
 * https://stackoverflow.com/questions/4662215/how-to-extract-a-substring-using-regex
 */
import com.sun.istack.internal.Nullable;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.*;

public class Node {

   private String name;
   private Node firstChild;
   private Node nextSibling;


   Node (String n, Node d, Node r) {
      setName(n);
      setFirstChild(d);
      setNextSibling(r);
   }

   private String getName() {
      return name;
   }

   private void setName(String name) {
      this.name = name;
   }

   private Node getFirstChild() {
      return firstChild;
   }

   private void setFirstChild(Node firstChild) {
      this.firstChild = firstChild;
   }

   private Node getNextSibling() {
      return nextSibling;
   }

   private void setNextSibling(Node nextSibling) {
      this.nextSibling = nextSibling;
   }

   //Recursive method that splits string and creates nodes
   public static Node parsePostfix (String s) {
      if (s.isEmpty()) return null;
      char[] charArray = s.toCharArray();
      StringBuilder currentBuffer = new StringBuilder();
      StringBuilder siblingBuffer = new StringBuilder();
      StringBuilder childBuffer = new StringBuilder();
      Boolean hasChild = false;
      int parenthesisPairCount = 0;
      for (int i = 0; i < charArray.length; i++) {
         if (String.valueOf(charArray[i]).equals("(") && !hasChild) {
            hasChild = true;
            parenthesisPairCount++;
         }else if (hasChild){
            if (String.valueOf(charArray[i]).equals("(")){
               parenthesisPairCount++;
            }else if (String.valueOf(charArray[i]).equals(")")){
               parenthesisPairCount--;
            }
            if (parenthesisPairCount <= 0){
               hasChild = false;
            } else {
               childBuffer.append(charArray[i]);
            }
         }else{
            if (String.valueOf(charArray[i]).equals(",")){
               for (int j = i+1; j < charArray.length; j++) {
                  siblingBuffer.append(charArray[j]);
               }
               break; // break out from loop (finish)
            }
            currentBuffer.append(charArray[i]);
         }
      }
      String currentNodeString = currentBuffer.toString();
      String childNodeString = childBuffer.toString();
      String siblingNodeString = siblingBuffer.toString();

      return new Node(currentNodeString, parsePostfix(childNodeString), parsePostfix(siblingNodeString));  // TODO!!! return the root
   }

   public String leftParentheticRepresentation() {
      StringBuilder tree = new StringBuilder();
      if (!this.name.isEmpty()){
         tree.append(this.getName());
         if (this.getFirstChild() != null){
            String child = this.getFirstChild().leftParentheticRepresentation();
            tree.append("(").append(child).append(")");
         }
         if (this.getNextSibling() != null){
            String sibling = this.getNextSibling().leftParentheticRepresentation();
            tree.append(",").append(sibling);
         }
      }
      return tree.toString(); // TODO!!! return the string without spaces
   }

   public static void main (String[] param) {
      String s = "A(B,(B2)C),B3(A2,C2)";
      Node t = Node.parsePostfix (s);
      String v = t.leftParentheticRepresentation();
      System.out.println (s + " ==> " + v); // (B1,C)A ==> A(B1,C)
   }

}