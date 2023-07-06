package org.example.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.example.domain.query.QOrderDetail;
import org.example.service.LoadExampleData;
import org.junit.jupiter.api.Test;

import io.ebean.DB;
import io.ebean.test.LoggedSql;

public class OrderDetailTest {

  @Test
  public void testSoftDeleteUsingDBFindWithFindCount() {
    LoadExampleData.load();
    LoggedSql.start();

    int containDeleted = DB.find(OrderDetail.class).where().eq("order.company.name", "test").findCount();
    int missingDeleted = DB.find(OrderDetail.class).where().eq("order.invoice.name", "test").findCount();
    
    List<String> sql = LoggedSql.stop();

    assertThat(sql).hasSize(2);
    // Success
    assertThat(sql.get(0)).contains("t2.deleted = false");
    // Missing deleted flag
    assertThat(sql.get(1)).contains("t2.deleted = false");
  }
  
  @Test
  public void testSoftDeleteUsingQueryBeanWithFindCount() {
    LoadExampleData.load();
    LoggedSql.start();

    int containDeleted = new QOrderDetail()
            .select("order.company.id, order.company.name")
            .order.company.name.eq("test")
            .findCount();
    
    int missingDeleted = new QOrderDetail()
            .select("order.invoice.id, order.invoice.name")
            .order.invoice.name.eq("test")
            .findCount();
    
    List<String> sql = LoggedSql.stop();

    assertThat(sql).hasSize(2);
    // Success
    assertThat(sql.get(0)).contains("t2.deleted = false");
    // Missing deleted flag
    assertThat(sql.get(1)).contains("t2.deleted = false");
  }
  
  @Test
  public void testSoftDeleteUsingQueryBeanWithFindList() {
    LoadExampleData.load();
    LoggedSql.start();

    List<OrderDetail> containDeleted = new QOrderDetail()
            .select("order.company.id, order.company.name")
            .order.company.name.eq("test")
            .findList();
    
    List<OrderDetail> missingDeleted = new QOrderDetail()
            .select("order.invoice.id, order.invoice.name")
            .order.invoice.name.eq("test")
            .findList();
    
    List<String> sql = LoggedSql.stop();

    assertThat(sql).hasSize(2);
    // Success
    assertThat(sql.get(0)).contains("t2.deleted = false");
    // Missing deleted flag
    assertThat(sql.get(1)).contains("t2.deleted = false");
  }
}
