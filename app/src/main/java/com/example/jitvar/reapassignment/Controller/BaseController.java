package com.example.jitvar.reapassignment.Controller;

import com.example.jitvar.reapassignment.Core.Model;
import com.example.jitvar.reapassignment.DAO.BaseDAO;
import com.example.jitvar.reapassignment.MainActivity;
import com.j256.ormlite.stmt.Where;


import java.sql.SQLException;
import java.util.List;

public class BaseController {

    private static BaseDAO baseDAO;

    public BaseController() {
        this.baseDAO = new BaseDAO(MainActivity.getDatabase());
    }

    public Model getEntityById(Class<? extends Model> classType, long id) {
        try {
            return baseDAO.find(classType, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Model getEntityByResourceID(Class modelClass,long resourceID) {
        try {
            return baseDAO.findByResourceID(modelClass, resourceID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<? extends Model> getEntities(Class<? extends Model> classType) {
        try {
            return baseDAO.findAll(classType);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<? extends Model> getEntities(Class<? extends Model> classType, Class<? extends Model> whereClassType, String whereColumn, long whereId) {
        try {
            Where where = baseDAO.getDbHelper().getDao(whereClassType).queryBuilder().where().eq(whereColumn, whereId);
            return baseDAO.findAll(classType, where);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void persistEntity(Model model) {
        try {
            baseDAO.persist(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateEntity(Model model) {
        try {
            baseDAO.update(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeEntity(Model model, boolean delete) {
        try {
            baseDAO.remove(model, delete);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
