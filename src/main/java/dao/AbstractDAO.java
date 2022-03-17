package dao;

import connection.ConnectionFactory;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Andrei Rotaru
 *
 * @param <T> is a generic representing the type associated to this object
 *
 * Class that uses reflection to generate the common SQL commands for all the entities types in this application
 */

public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
    private final Class<T> type;

    /**
     * Constructor of the class, sets the type of the object received as generic
     */
    @SuppressWarnings("unchecked")
    public AbstractDAO(){
        this.type = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Method handling the INSERT SQL command
     * @param obj represents the object that needs to be inserted into the database
     */
    public void insertEntity(T obj){
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createInsertQuery(obj);
        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.executeUpdate();
        }catch(SQLException e){
            LOGGER.log(Level.WARNING, type.getName() + " DAO:insertValues " + e.getMessage());
        }finally{
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Method creating the INSERT SQL command through reflection
     * @param obj represents the object for which the INSERT is required
     * @return returns a String object representing the INSERT command
     */
    private String createInsertQuery(T obj){
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append("schooldb3.");
        sb.append(type.getSimpleName());
        sb.append("(");

        for(Field field:this.type.getDeclaredFields()){
            if(!field.getName().equals("id")){
                sb.append(field.getName() + ",");
            }
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append(") VALUES(");

        for(Field field:obj.getClass().getDeclaredFields()){
            if(!field.getName().equals("id")){
                this.appendByFieldType(obj, field, sb);
                sb.append(",");
            }
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append(")");

        return sb.toString();
    }

    /**
     * Method handling the DELETE SQL command
     * @param obj represents the object that needs to be deleted from the database
     */
    public void deleteEntity(T obj){
        Connection connection = null;
        PreparedStatement statement = null;
        String query;
        try {
            query = createDeleteQuery(obj, obj.getClass().getDeclaredField("id"));

            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.executeUpdate();
        } catch (NoSuchFieldException e1) {
            e1.printStackTrace();
        } catch (SecurityException e1) {
            e1.printStackTrace();
        } catch(SQLException e){
            LOGGER.log(Level.WARNING, type.getName() + " DAO:deleteByField " + e.getMessage());
        }finally{
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Method creating the DELETE SQL command through reflection
     * @param obj represents the object for which the DELETE is required
     * @param field represents the filtering criteria for the DELETE command
     * @return returns a String object representing the DELETE command
     */
    private String createDeleteQuery(T obj, Field field){
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE ");
        sb.append(" FROM ");
        sb.append("schooldb3.");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field.getName() + " = ");
        this.appendByFieldType(obj, field, sb);

        return sb.toString();
    }

    /**
     * Method handling the UPDATE SQL command
     * @param obj represents the object that needs to be updated in the database
     */
    public void updateEntity(T obj){
        Connection connection = null;
        PreparedStatement statement = null;

        try{
            String query = createUpdateQuery(obj, obj.getClass().getDeclaredField("id"));

            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.executeUpdate();
        }catch(SQLException e){
            LOGGER.log(Level.WARNING, type.getName() + " DAO:insertValues " + e.getMessage());
        }catch(NoSuchFieldException e1){
            e1.printStackTrace();
        }finally{
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Method creating the UPDATE SQL command through reflection
     * @param obj represents the object for which the UPDATE is required
     * @param searchField represents the filtering criteria for the UPDATE command
     * @return returns a String object representing the DELETE command
     */
    private String createUpdateQuery(T obj, Field searchField){
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append("schooldb3.");
        sb.append(type.getSimpleName());
        sb.append(" SET ");
        for(Field field:this.type.getDeclaredFields()){
            if(!field.getName().equals("id")) {
                for (Field fieldObj : obj.getClass().getDeclaredFields()) {
                    if (fieldObj.getName().equals(field.getName())) {
                        sb.append(field.getName() + "=");
                        this.appendByFieldType(obj, fieldObj, sb);
                        sb.append(", ");
                    }
                }
            }
        }

        sb.deleteCharAt(sb.length()-2);
        sb.deleteCharAt(sb.length()-1);
        sb.append(" WHERE " + searchField.getName() + " = ");
        this.appendByFieldType(obj,searchField,sb);

        return sb.toString();
    }

    private List<T> createObjects(ResultSet resultSet){
        List<T> list = new ArrayList<T>();
        try{
            while(resultSet.next()){
                T instance = type.getDeclaredConstructor().newInstance();
                for(Field field: type.getDeclaredFields()){
                    Object value = resultSet.getObject(field.getName());
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                    Method method = propertyDescriptor.getWriteMethod();

                    if(String.class.isAssignableFrom(field.getType())){
                        method.invoke(instance, value);
                    }
                    else if(field.getType().isAssignableFrom(int.class)){
                        method.invoke(instance, value);
                    }
                    else if(field.getType().isAssignableFrom(float.class)){
                        method.invoke(instance, value);
                    }
                }
                list.add(instance);
            }
            return list;
        }catch(InstantiationException e){
            e.printStackTrace();
        }catch(InvocationTargetException e){
            e.printStackTrace();
        }catch(IllegalAccessException e){
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String createSelectQuery(T obj, Field field){
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM  ");
        sb.append("schooldb3.");
        sb.append(type.getSimpleName());
        if(obj != null){
            sb.append(" WHERE ");
            sb.append(field.getName() + " = ");
            this.appendByFieldType(obj, field, sb);
        }
        return sb.toString();
    }

    /**
     * Method that decides how the field value will be appended to the SQL query.
     * Adds double quotes in case of String values
     * @param obj the object for which the value to be appended is required
     * @param field the field for which the value to be appended is required
     * @param sb the StringBuilder object that appends the field value
     */
    private void appendByFieldType(T obj, Field field, StringBuilder sb){
        if(field.getType().isAssignableFrom(String.class)){
            sb.append("\"");
            sb.append(getFieldValue(obj, field));
            sb.append("\"");
        }
        else{
            sb.append(getFieldValue(obj, field));
        }
    }

    /**
     * Method that gets the value of the required field from the given object, depending on the type of that field
     * @param obj represents the object for which the field value is required
     * @param field represents the field for which the value is required
     * @return a String object with the field value. This value is used in generating queries
     */
    private String getFieldValue(T obj, Field field){
        String stringVal = "";
        double doubleVal;
        int intVal;

        try {
            if(field.getType().isAssignableFrom(String.class)){
                Field privateField = obj.getClass().getDeclaredField(field.getName());
                privateField.setAccessible(true);
                stringVal = (String)privateField.get(obj);
            }
            else if(field.getType().isAssignableFrom(double.class)){
                Field privateField = obj.getClass().getDeclaredField(field.getName());
                privateField.setAccessible(true);
                doubleVal = (Double)privateField.get(obj);
                stringVal = Double.toString(doubleVal);
            }
            else if(field.getType().isAssignableFrom(int.class)){
                Field privateField = obj.getClass().getDeclaredField(field.getName());
                privateField.setAccessible(true);
                intVal = (Integer)privateField.get(obj);
                stringVal = Integer.toString(intVal);
            }

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return stringVal;
    }
}
