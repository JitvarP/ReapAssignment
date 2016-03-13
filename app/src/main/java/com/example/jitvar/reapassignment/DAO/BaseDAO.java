
package com.example.jitvar.reapassignment.DAO;

import android.util.Log;

import com.example.jitvar.reapassignment.Core.Model;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;


import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by root on 3/10/15.
 */
public class BaseDAO {
    private static final String TAG = BaseDAO.class.getSimpleName();
    private OrmLiteSqliteOpenHelper dbHelper;

    public BaseDAO(OrmLiteSqliteOpenHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public OrmLiteSqliteOpenHelper getDbHelper() {
        return dbHelper;
    }

    public void setDbHelper(OrmLiteSqliteOpenHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void persist(Model model) throws SQLException {
        Dao dao = dbHelper.getDao(model.getClass());
        dao.create(model);
    }

    public void createOrUpdate(Model model) throws SQLException{
        Dao dao = dbHelper.getDao(model.getClass());
        dao.createOrUpdate(model);
    }


    public void update(Model model) throws SQLException {
        Dao dao = dbHelper.getDao(model.getClass());

        Model modelFromDb = find(model.getClass(), model.getId());
        if (model.getResourceId() == 0 && modelFromDb.getResourceId() != 0) {
            model.setResourceId(modelFromDb.getResourceId());
        }

        if (model.getVersion() < modelFromDb.getVersion()) {
            Log.i(TAG, "updating stale data");
            model.setVersion(modelFromDb.getVersion());
        }

        dao.update(model);
    }

    public void remove(Model model, boolean delete) throws SQLException {
        Dao dao = dbHelper.getDao(model.getClass());
        if (delete) {
            dao.delete(model);
        }
        else {
            model.setActive(false);
            dao.update(model);
        }
    }

    public void remove(Class<? extends Model> classType, Where where, boolean delete) throws SQLException {
        Dao dao = dbHelper.getDao(classType);

        DeleteBuilder deleteBuilder = dao.deleteBuilder();
        if (delete) {
            deleteBuilder.setWhere(where);
            dao.delete(deleteBuilder.prepare());
        }
        else {
            where.and()
                    .eq(Model.COLUMN_ACTIVE, false);
            deleteBuilder.setWhere(where);
            dao.update(deleteBuilder.prepare());
        }
    }

    @SuppressWarnings({ "rawtypes" })
    public List findAll(Class<? extends Model> classType) throws SQLException {
        Dao dao = dbHelper.getDao(classType);
        List modelList = dao.queryForAll();
        return modelList;
    }


    public List findAll(Class<? extends Model> classType, Where where)
            throws SQLException {
        Dao dao = dbHelper.getDao(classType);
        QueryBuilder queryBuilder = dao.queryBuilder();
        if (where != null) {
            queryBuilder.setWhere(where);
        }

        List modelList = dao.query(queryBuilder.prepare());
        return modelList;
    }


    public long count(Class<? extends Model> classType) throws SQLException {
        Dao dao = dbHelper.getDao(classType);
        return dao.countOf();
    }

    public long count(Class<? extends Model> classType, Where where) throws SQLException {
        Dao dao = dbHelper.getDao(classType);
        QueryBuilder queryBuilder = dao.queryBuilder();
        if (where != null) {
            queryBuilder.setWhere(where);
        }
        return dao.countOf(queryBuilder.prepare());
    }

    @SuppressWarnings("unchecked")
    public List findAll(Class<? extends Model> classType, Where where, String orderColumnName, boolean ascending)
            throws SQLException {
        Dao dao = dbHelper.getDao(classType);
        QueryBuilder queryBuilder = dao.queryBuilder();
        if (where != null) {
            queryBuilder.setWhere(where);
        }
        if (orderColumnName != null && !orderColumnName.equals("")) {
            queryBuilder.orderBy(orderColumnName, ascending);
        }

        List modelList = dao.query(queryBuilder.prepare());
        return modelList;
    }

    public List findAll(Class classType, String[] columns, Where where, String orderColumnName, boolean ascending, long limit) throws SQLException {
        Dao dao = dbHelper.getDao(classType);
        QueryBuilder queryBuilder = dao.queryBuilder();
        if (orderColumnName != null && !orderColumnName.equals("")) {
            queryBuilder.orderBy(orderColumnName, ascending);
        }
        queryBuilder.limit(limit);

        if (where != null) {
            queryBuilder.setWhere(where);
        }

        if (columns != null) {
            //Add each column to the queryBuilder.
            for (int i = 0; i < columns.length; i++) {
                queryBuilder.selectColumns(columns[i]);
            }
        }

        List modelList = dao.query(queryBuilder.prepare());
        return modelList;
    }

    public List findAll(Class<? extends Model> classType, String orderColumnName, boolean ascending)
            throws SQLException {
        Dao dao = dbHelper.getDao(classType);
        QueryBuilder queryBuilder = dao.queryBuilder();
        if (orderColumnName != null && !orderColumnName.equals("")) {
            queryBuilder.orderBy(orderColumnName, ascending);
        }

        List modelList = dao.query(queryBuilder.prepare());
        return modelList;
    }
    public List findAll(Class<? extends Model> classType, String[] columns, Where where) throws SQLException {
        Dao dao = dbHelper.getDao(classType);
        QueryBuilder queryBuilder = dao.queryBuilder();
        if (where != null) {
            queryBuilder.setWhere(where);
        }

        if (columns != null) {
            //Add each column to the queryBuilder.
            for (int i = 0; i < columns.length; i++) {
                queryBuilder.selectColumns(columns[i]);
            }
        }

        List modelList = dao.query(queryBuilder.prepare());
        return modelList;
    }

    public Model find(Class<? extends Model> classType, String[] columns, Where where) throws SQLException {
        Dao dao = dbHelper.getDao(classType);
        QueryBuilder queryBuilder = dao.queryBuilder();
        if (where != null) {
            queryBuilder.setWhere(where);
        }

        if (columns != null) {
            //Add each column to the queryBuilder.
            for (int i = 0; i < columns.length; i++) {
                queryBuilder.selectColumns(columns[i]);
            }
        }

        Model model = (Model) dao.queryForFirst(queryBuilder.prepare());
        return model;
    }

    public Model find(Class<? extends Model> classType, String[] columns, long id) throws SQLException {
        Dao dao = dbHelper.getDao(classType);
        QueryBuilder queryBuilder = dao.queryBuilder();

        Where where = queryBuilder.where();
        where.eq(Model.COLUMN_ID, id);
        queryBuilder.setWhere(where);

        if (columns != null) {
            //Add each column to the queryBuilder.
            for (int i = 0; i < columns.length; i++) {
                queryBuilder.selectColumns(columns[i]);
            }
        }

        Model model = (Model) dao.queryForFirst(queryBuilder.prepare());
        return model;
    }

    public Model find(Class<? extends Model> classType, long id) throws SQLException {
        Dao dao = dbHelper.getDao(classType);

        Model model = (Model) dao.queryForId(id);
        return model;
    }

    public Model find(Class classType, Where where) throws SQLException {
        Dao dao = dbHelper.getDao(classType);
        QueryBuilder queryBuilder = dao.queryBuilder();
        if (where != null) {
            queryBuilder.setWhere(where);
        }
        Model model = (Model) dao.queryForFirst(queryBuilder.prepare());
        return model;
    }

    public Model findByResourceID(Class<? extends Model> classType, long resourceID) throws SQLException {
        Dao dao = dbHelper.getDao(classType);
        QueryBuilder queryBuilder = dao.queryBuilder();
        Where where = queryBuilder.where();
        where.eq(Model.COLUMN_RESOURCE_ID, resourceID);

        Model model = find(classType, where);
        return model;

    }

    public void update(Class<? extends Model> classType, Where where, HashMap<String, Object> updateColumnsMap) throws SQLException,
            NullPointerException {
        Dao dao = dbHelper.getDao(classType);
        UpdateBuilder updateBuilder = dao.updateBuilder();
        if (where != null) {
            updateBuilder.setWhere(where);
        }

        Set<String> columnNames = updateColumnsMap.keySet();
        Iterator<String> columnNamesIterator = columnNames.iterator();
        while (columnNamesIterator.hasNext()) {
            String columnName = columnNamesIterator.next();
            Object columnValue = updateColumnsMap.get(columnName);

            updateBuilder.updateColumnValue(columnName, columnValue);
        }
        updateBuilder.update();
    }
}
