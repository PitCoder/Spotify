package admin;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Stack;

import org.json.JSONArray;

import narytree.N_AryTree;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetProcessedDataServlet
 */
@WebServlet(urlPatterns="/GetProcessedDataServlet")
public class GetProcessedDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetProcessedDataServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintStream output = new PrintStream(new File("log.txt"));
		PrintStream console = System.out;
		System.setOut(console);
		
		AdminDashboardService admonservice = new AdminDashboardService();
		ArrayList<ArrayList<String>> set = new ArrayList<>();
		ArrayList<String> attribute;
		String data = "";
		
		
		  try {
		    BufferedReader reader = request.getReader();
		    JSONArray jsonArray = new JSONArray(reader.readLine());
		    JSONArray innerJsonArray;
		    
		    
		    for(int i = 0; i < jsonArray.length(); i++) {
		    	attribute = new ArrayList<>();
		    	innerJsonArray = jsonArray.getJSONArray(i);
		    	
		    	for(int j = 0; j < innerJsonArray.length(); j++) {
		    		data = innerJsonArray.getString(j);
		    		attribute.add(data);
		    		System.out.println(data);
		    	}
		    	set.add(attribute);
		    }
		    System.setOut(output);
		    String decisionTree = admonservice.getDecisionTree(set);
		    System.setOut(console);
		    
		    //System.out.println(decisionTree);	    
		    //N_AryTree<String> deserialized = deserializeTree(decisionTree);
	        //deserialized.print("", true, deserialized);
	        response.setContentType("application/text");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(print("",true,admonservice.getDecisionTree()));
		  } 
		  catch (IOException e) { 
			  e.printStackTrace();
		  }
	}
	
	public String print(String prefix, boolean isTail, N_AryTree tree) {
		  StringBuilder sb = new StringBuilder();
	      sb.append(prefix + (isTail ? "└── " : "├── ") + tree.getData() + "\n");
	      for (int i = 0; i < tree.getNumberOfChildren() - 1; i++) {
	         sb.append(print(prefix + (isTail ? "    " : "│   "), false, tree.getChildAt(i)));
	      }
	      if (tree.getNumberOfChildren() > 0) {
	         sb.append(print(prefix + (isTail ? "    " : "│   "), true, tree.getChildAt(tree.getNumberOfChildren() - 1)));
	      }
	   return sb.toString();
	}
	
	public N_AryTree<String> deserializeTree(String serialed) {
		Stack stack = new Stack();
		String targetString = serialed.substring(0, serialed.length()-1);
		int i=0;
		int firstIndex = i;
		
		while(targetString.charAt(i)!=',')
			i++;
		
		stack.push(new N_AryTree<String>(targetString.substring(firstIndex,i)));
		i=i+1;
		firstIndex = i;
		
		while (i < targetString.length()) {
			if (targetString.charAt(i) == ',') {
				i++;
				continue;
			}
			
			if (targetString.charAt(i) == ')') {
				stack.pop();
				i++;
				continue;
			}
			
			firstIndex = i;
			while(targetString.charAt(i) != ',' && targetString.charAt(i) != '(')
				i++;
			
			String temp = targetString.substring(firstIndex,i);
			N_AryTree<String> parent = (N_AryTree<String>) stack.peek();
			N_AryTree<String> child = new N_AryTree<String>(temp);
			parent.addChild(child);
			stack.push(child);
		}
		return (N_AryTree<String>) stack.pop();
	}
}
