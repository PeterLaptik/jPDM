package jpdm.db.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.UUID;

import org.junit.Test;

import by.jpdm.model.beans.scheme.PropertyType;
import jpdm.db.modeller.tree.ModelTypeNode;
import jpdm.db.modeller.tree.ModelTypeProperty;
import jpdm.db.modeller.tree.ModelUpdatingException;

public class TestModelMapping {

    @Test
    @SuppressWarnings("unused")
    public void testNodesCreating() throws ModelUpdatingException, NoSuchFieldException, SecurityException,
            IllegalArgumentException, IllegalAccessException {
        ModelTypeNode root = ModelTypeNode.createRoot();
        ModelTypeNode item = root.addChild("Item");
        ModelTypeNode asm = item.addChild("Assembly");
        ModelTypeNode detail = item.addChild("Detail");
        root.addProperty(new ModelTypeProperty("name", PropertyType.VARCHAR_256, UUID.randomUUID()));
        detail.addProperty(new ModelTypeProperty("det_type", PropertyType.VARCHAR_256, UUID.randomUUID()));

        testTypeError1();
        testTypeError2();

        testPropertyError();
    }

    @SuppressWarnings("unused")
    private void testTypeError1() throws ModelUpdatingException {
        Exception e = null;
        try {
            ModelTypeNode root = ModelTypeNode.createRoot();
            ModelTypeNode item = root.addChild("Item");
            ModelTypeNode asm = item.addChild("Assembly");
            ModelTypeNode detail = item.addChild("Detail");
            ModelTypeNode detailx = item.addChild("Detail");
        } catch (Exception ex) {
            e = ex;
        }
        assertNotEquals("Expecting exception is failed", e, null);
        assertEquals("Exception type check is failed", e.getClass(), ModelUpdatingException.class);

    }

    @SuppressWarnings("unused")
    private void testTypeError2() throws ModelUpdatingException {
        Exception e = null;
        try {
            ModelTypeNode root = ModelTypeNode.createRoot();
            ModelTypeNode item = root.addChild("Item");
            ModelTypeNode asm = item.addChild("Assembly");
            ModelTypeNode detail = asm.addChild("Detail");
            ModelTypeNode detailx = item.addChild("Detail");
        } catch (Exception ex) {
            e = ex;
        }
        assertNotEquals("Expecting exception is failed", e, null);
        assertEquals("Exception type check is failed", e.getClass(), ModelUpdatingException.class);
    }

    @SuppressWarnings("unused")
    private void testPropertyError() throws ModelUpdatingException {
        Exception e = null;
        try {
            ModelTypeNode root = ModelTypeNode.createRoot();
            ModelTypeNode item = root.addChild("Item");
            ModelTypeNode asm = item.addChild("Assembly");
            ModelTypeNode detail = item.addChild("Detail");
            root.addProperty(new ModelTypeProperty("name", PropertyType.VARCHAR_256, UUID.randomUUID()));
            detail.addProperty(new ModelTypeProperty("det_type", PropertyType.VARCHAR_256, UUID.randomUUID()));
            detail.addProperty(new ModelTypeProperty("name", PropertyType.VARCHAR_256, UUID.randomUUID()));
        } catch (Exception ex) {
            e = ex;
        }
        assertNotEquals("Expecting exception is failed", e, null);
        assertEquals("Exception type check is failed", e.getClass(), ModelUpdatingException.class);
    }

    @Test
    public void testNodesBuilderIntegration() throws SQLException, ModelUpdatingException {
        final String url = "jdbc:postgresql://127.0.0.1:5432/";
        final String user = "postgres";
        final String pass = "postgres";
        prepareIntegrationEnvironment(url, user, pass);
//		ModelDriver driver = new ModelDriver(url, user, pass);
//		ModelTypeNode root = driver.buildModelTree();
//		root.toConsole();
    }

    private void prepareIntegrationEnvironment(String url, String user, String pass) throws SQLException {
        final String testDb = "pdm_test";
        Connection conn = DriverManager.getConnection(url, user, pass);
        conn.createStatement().execute(String.format("DROP DATABASE IF EXISTS %s;", testDb));
        conn.createStatement().execute(String.format("CREATE DATABASE %s;", testDb));
        conn.close();

        conn = DriverManager.getConnection(url + testDb, user, pass);

    }
}
