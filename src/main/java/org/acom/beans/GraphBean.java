package org.acom.beans;

import org.acom.Exception.InvalidDateException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class GraphBean {
    int id;
    int avg;
    String startDate;
    String endDate;
    int width;
    int height;
    String graphStyling;
    ConfigBean configBean;
    String username;
    String password;
    int[] hide;

    public GraphBean() {
    }

    public GraphBean(int id, String startDate, String endDate, int avg, int width, int height, String graphStyling, String username, String password, int[] hide) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.avg = avg;
        this.width = width;
        this.height = height;
        this.graphStyling = graphStyling;
        this.username = username;
        this.password = password;
        this.hide = hide;
    }

    public GraphBean(int id, String startDate, String endDate, int avg, int width, int height, String graphStyling, ConfigBean configBean, int[] hide) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.avg = avg;
        this.width = width;
        this.height = height;
        this.graphStyling = graphStyling;
        this.configBean = configBean;
        this.hide = hide;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartDate() throws InvalidDateException {
        if (startDate != null) {
            try {
                Date inputDate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").parse(startDate);
                Date currentDate = new Date();
                Date end = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").parse(getEndDate());

                if (inputDate.before(currentDate) && inputDate.before(end)) {
                    return startDate;
                } else {
                    throw new InvalidDateException("Error: Input date should not be greater than current system time or end time.");
                }
            } catch (ParseException e) {
                System.out.println("Error: Invalid date format. Please use 'yyyy-MM-dd-HH-mm-ss'.");
                throw new RuntimeException(e);
            }
        } else {
            return new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        }
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() throws InvalidDateException {
        if (endDate != null && startDate != null) {
            try {
                Date inputDate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").parse(endDate);
                Date currentDate = new Date();
                Date start = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").parse(startDate);

                if (inputDate.before(currentDate) && inputDate.after(start)) {
                    return endDate;
                } else {
                    throw new InvalidDateException("Error: The input date should not be greater than the current system time or less than the starting time.");
                }
            } catch (ParseException e) {
                System.out.println("Error: Invalid date format. Please use 'yyyy-MM-dd-HH-mm-ss'.");
                throw new RuntimeException(e);
            }
        } else {
            return new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        }
    }

    public void setEndDate(String endDate) throws InvalidDateException {
        this.endDate = endDate;
    }

    public int getAvg() {
        return avg == 0 ? 86400 : avg;
    }

    public void setAvg(int avg) {
        this.avg = avg;
    }

    public int getWidth() {
        return width == 0 ? 975 : width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height == 0 ? 300 : height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getGraphStyling() {
//        return graphStyling == null ? "graphstyling=baseFontSize%3D%2710%27%20showLegend%3D%271%27" : graphStyling;
        return graphStyling == null ? "baseFontSize%3D%2710%27%20showLegend%3D%271%27" : graphStyling;
    }

    public void setGraphStyling(String graphStyling) {
        this.graphStyling = graphStyling;
    }

    public String getUsername() {
        return null == configBean ? username : configBean.getUsername();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return null == configBean ? password : configBean.getPassword();
    }

    public void setPassword(String password) {
        this.password =  password;
    }

    public int[] getHide() {
        return (hide != null && hide.length > 0) ? hide : new int[]{-4};
    }

    public void setHide(int[] hide) {
        this.hide = hide;
    }

    public ConfigBean getConfigBean() {
        return configBean;
    }

    public void setConfigBean(ConfigBean configBean) {
        this.configBean = configBean;
    }

    @Override
    public String toString() {
        return "GraphBean{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", width=" + width +
                ", height=" + height +
                ", graphStyling='" + graphStyling + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", hide=" + Arrays.toString(hide) +
                '}';
    }
}
