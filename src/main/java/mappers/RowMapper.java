/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package mappers;

import java.sql.ResultSet;

/**
 *
 * @author tinhlam
 * @param <T>
 */
public interface RowMapper<T> {
    public T mapRow(ResultSet res);
}
