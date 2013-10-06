package translator;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.http.HttpServletResponse;

import com.hackmit.blockbuilding.BlockBuildingServlet;

public class Translator {

	private final HttpServletResponse out;

	public Translator(HttpServletResponse resp) {
		this.out = resp;
	}

	public void translateCursor(String x, String y) {
		// where to move block!
		try {
			out.getWriter().println(
					"{'response':{'x':" + x + ",'y':" + y + "}}");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void translateClick(String prevX, String prevY) {
		// where to paste the block!
		try {
			out.getWriter().println(
					"{'response':{'x':" + prevX + ",'y':" + prevY + "}}");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
