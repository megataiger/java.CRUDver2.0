package mvc.wrappers;

import org.springframework.data.domain.Sort;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class DataTablesWrapper {

    private String draw;

    private int page;

    private int length;

    private String filter;

    private List<Sort.Order> orders = new ArrayList<>();

    public DataTablesWrapper(HttpServletRequest request) {
        orders = parse(request);
        draw = request.getParameter("draw");
        page = Integer.parseInt(request.getParameter("start"))/10;
        length = Integer.parseInt(request.getParameter("length"));
        filter = "%" + request.getParameter("search[value]") + "%";
    }

    public List<Sort.Order> getOrders() {
            return orders;
        }

    public String getDraw() {
            return draw;
        }

    public int getPage() {
            return page;
        }

    public int getLength() {
            return length;
        }

    public String getFilter() {
            return filter;
        }

    private List<Sort.Order> parse(HttpServletRequest request) {
        List<Sort.Order> list = new ArrayList<>();
        int i = 0;
        while (request.getParameter("order[" + i + "][dir]") != null) {
            int columnNumber = Integer.parseInt(request.getParameter("order[" + i + "][column]"));
            if (request.getParameter("order[" + i + "][dir]").equals("asc")) {
                list.add(Sort.Order.asc(request.getParameter("columns[" + columnNumber + "][name]")));
            } else {
                list.add(Sort.Order.desc(request.getParameter("columns[" + columnNumber + "][name]")));
            }
            i++;
        }
        return list;
    }
}