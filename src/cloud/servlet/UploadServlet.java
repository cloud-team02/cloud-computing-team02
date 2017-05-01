package cloud.servlet;

import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.servlet.annotation.MultipartConfig;

import java.util.Enumeration;
//import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import cloud.clouddb.cloud_app.*;
import cloud.clouddb.cloud_user.*;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/UploadServlet")
@MultipartConfig(fileSizeThreshold=1024*1024*2, maxFileSize=1024*1024*10, maxRequestSize=1024*1024*50)
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SAVE_DIR = "uploadedFiles";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadServlet() {
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
		// TODO Auto-generated method stub
		AppDAO appDAO = new AppDAO();
	    App newApp = new App();
	    newApp.setApp_name(request.getParameter("AppsName"));
	    newApp.setPrice(Integer.parseInt(request.getParameter("price")));
	    newApp.setDescription(request.getParameter("description"));
	    newApp.setUse_times(0);
	    HttpSession session = request.getSession();
	    User developer = (User)session.getAttribute("currentSessionUser");
	    //newApp.setDeveloper_id(developer.getId());
		//String appPath = request.getServletContext().getRealPath("");
	    // Create path to the directory to save uploaded file
		String savePath = "/home/sz144/tomcat/webapps/"+request.getParameter("AppsName"); //appPath + File.separator + SAVE_DIR;    
	    // Create the save directory if it does not exist
		File fileSaveDir = new File(savePath);
		if (!fileSaveDir.exists())
			fileSaveDir.mkdir();
		newApp.setUrl(savePath);
		Part appPart = request.getPart("app_file");
		Part iconPart = request.getPart("icon_file");
		String fileName = extractFileName(appPart);
		String iconName = extractFileName(iconPart);
		String appPath = savePath + File.separator + fileName;
		String iconPath = savePath + File.separator + iconName;
		newApp.setIconUrl(iconPath);
		appDAO.addApp(newApp);
		appPart.write(appPath);
		jarExtractor(appPath,savePath);
		appPart.write(iconPath);
		/*
		for (Part part : request.getParts()){
			String fileName = extractFileName(part);
			System.out.println(fileName);
			part.write(savePath + File.separator + fileName);
		}
		*/
		
		//response.getWriter().append(savePath);

	}
	private String extractFileName(Part part) {
	    String contentDisp = part.getHeader("content-disposition");
	    String[] items = contentDisp.split(";");
	    for (String s : items) {
	      if (s.trim().startsWith("filename")) {
	        return s.substring(s.indexOf("=") + 2, s.length()-1);
	      }
	    }
	    return "";
	  }
	

		//private statiString jarpath = "/home/sz144/tomcat/webapps/Due.jar";
		//private static String destdir = "/home/sz144/tomcat/webapps/Due/";


	  	// Main method extracts a jarfile to a set of
	  	// directories under the target/path/ directory

	  	public void jarExtractor(String jarpath, String destdir) throws IOException {
	  		JarFile jarfile = new JarFile(jarpath);   
		  	for (Enumeration<JarEntry> iter = jarfile.entries(); 
		  			iter.hasMoreElements();) {   
		  		JarEntry entry = iter.nextElement();   
		  	    System.out.println("Processing: " + entry.getName());  
		  	    File outfile = new File(destdir, entry.getName());
		  	    if (! outfile.exists())
		  	    	outfile.getParentFile().mkdirs(); // create dir structure

		  	    // method body continued on next slide...
		  	    // continuation of main method

		  	    if (! entry.isDirectory()) {
		  	      InputStream instream = jarfile.getInputStream(entry);
		  	      FileOutputStream outstream = new FileOutputStream(outfile);
		  	      while (instream.available() > 0) { 
		  	          outstream.write(instream.read());
		  	      }
		  	      outstream.close();
		  	      instream.close();
		  	    }  // end if
		  	  }  // end for
		  	  jarfile.close();
		  	}  // end main // end class


}
