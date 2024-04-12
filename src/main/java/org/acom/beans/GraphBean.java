package org.acom.beans;

import org.acom.Exception.InvalidDateException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class GraphBean {
    String id;
    String avg;
    String startDate;
    String endDate;
    int width;
    int height;
    String graphStyling;
    ConfigBean configBean;
    String username;
    String password;
    String[] hide;


    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    public GraphBean() {
    }

    public GraphBean(String id, String avg, String startDate, String endDate, int width, int height, String graphStyling, ConfigBean configBean, String username, String password, String[] hide, SimpleDateFormat sdf) {
        this.id = id;
        this.avg = avg;
        this.startDate = startDate;
        this.endDate = endDate;
        this.width = width;
        this.height = height;
        this.graphStyling = graphStyling;
        this.configBean = configBean;
        this.username = username;
        this.password = password;
        this.hide = hide;
        this.sdf = sdf;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartDate() throws InvalidDateException {
        if (startDate != null && !startDate.isEmpty()) {
            try {
                sdf.setLenient(false);
                Date inputDate = sdf.parse(startDate);
                Date currentDate = new Date();
                Date end = sdf.parse(getEndDate());

                if (inputDate.before(currentDate) && inputDate.before(end)) {
                    return startDate;
                } else {
                    throw new InvalidDateException("Error: Input date should not be greater than current system time or end time.");
                }
            } catch (ParseException e) {
                System.out.println("Error: Invalid date format. Please format to 'yyyy-MM-dd-HH-mm-ss'.");
                throw new RuntimeException(e);
            }
        } else {
            return new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date().getTime()-60*60*24*30*1000L);
        }
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() throws InvalidDateException {
        if (endDate != null && !endDate.isEmpty()) {
            try {
                Date inputDate = sdf.parse(endDate);
                Date currentDate = new Date();
                Date start = sdf.parse(startDate.isEmpty() ? new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()) : startDate);

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

    public String getAvg() {
        return avg.equals("0") ? "86400" : avg;
    }

    public void setAvg(String avg) {
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

    public String[] getHide() {
        return (hide != null && hide.length > 0) ? hide : new String[]{"-4"};
    }

    public void setHide(String[] hide) {
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
