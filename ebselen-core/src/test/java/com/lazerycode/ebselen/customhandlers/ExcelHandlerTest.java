/*
 * Copyright (c) 2010-2011 Ardesco Solutions - http://www.ardescosolutions.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lazerycode.ebselen.customhandlers;

import jxl.Cell;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.HashMap;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class ExcelHandlerTest {

    private final URL excelFile = this.getClass().getResource("/web/xls/TestData.xls");

    @Test
    public void selectAnExcelSheet() throws Exception {
        ExcelHandler testExcelFile = new ExcelHandler(new File(excelFile.toURI()));
        testExcelFile.selectSheet("Blank Sheet");
        assertThat(testExcelFile.selectedSheetName(), is(equalTo("Blank Sheet")));
    }

    @Test
    public void getCellData() throws Exception {
        ExcelHandler testExcelFile = new ExcelHandler(new File(excelFile.toURI()));
        testExcelFile.selectSheet("Data Sheet");
        assertThat(testExcelFile.getCellData(1, 1).getContents(), is(equalTo("Test Data")));
        assertThat(testExcelFile.getCellData(1, 2).getContents(), is(equalTo("1")));
    }

    @Test
    public void getRowData() throws Exception {
        ExcelHandler testExcelFile = new ExcelHandler(new File(excelFile.toURI()));
        testExcelFile.selectSheet("Data Sheet");
        assertThat(testExcelFile.getRow(1).size(), is(equalTo(2)));
        assertThat(testExcelFile.getRow(1).get(2).getContents(), is(equalTo("More Test Data")));
    }

    @Test
    public void getColumnData() throws Exception {
        ExcelHandler testExcelFile = new ExcelHandler(new File(excelFile.toURI()));
        testExcelFile.selectSheet("Data Sheet");
        assertThat(testExcelFile.getColumn(1).size(), is(equalTo(4)));
        assertThat(testExcelFile.getColumn(1).get(1).getContents(), is(equalTo("Test Data")));
    }

    @Test
    public void getRowDataSkipFirstColumn() throws Exception {
        ExcelHandler testExcelFile = new ExcelHandler(new File(excelFile.toURI()));
        testExcelFile.selectSheet("Data Sheet");
        assertThat(testExcelFile.getRow(1, true).size(), is(equalTo(1)));
        assertThat(testExcelFile.getRow(1, true).get(2).getContents(), is(equalTo("More Test Data")));
    }

    @Test
    public void getColumnDataSkipFirstRow() throws Exception {
        ExcelHandler testExcelFile = new ExcelHandler(new File(excelFile.toURI()));
        testExcelFile.selectSheet("Data Sheet");
        assertThat(testExcelFile.getColumn(1, true).size(), is(equalTo(3)));
        assertThat(testExcelFile.getColumn(1, true).get(2).getContents(), is(equalTo("1")));
    }

    public void getTwoColumnsOfDataSkipFirstRow() throws Exception {
        ExcelHandler testExcelFile = new ExcelHandler(new File(excelFile.toURI()));
        testExcelFile.selectSheet("Data Sheet");
        HashMap<String, Cell> columnData = testExcelFile.mapTwoColumns(2, 1, true);
        assertThat(columnData.size(), is(equalTo(3)));
        assertThat(columnData.get("cat").getContents(), is(equalTo("1")));
        assertThat(columnData.get("horse").getContents(), is(equalTo("3")));
    }

    public void getTwoColumnsOfData() throws Exception {
        ExcelHandler testExcelFile = new ExcelHandler(new File(excelFile.toURI()));
        testExcelFile.selectSheet("Data Sheet");
        HashMap<String, Cell> columnData = testExcelFile.mapTwoColumns(2, 1);
        assertThat(columnData.size(), is(equalTo(4)));
        assertThat(columnData.get("cat").getContents(), is(equalTo("1")));
        assertThat(columnData.get("horse").getContents(), is(equalTo("3")));
    }

    public void getTwoRowsOfDataSkipFirstRow() throws Exception {
        ExcelHandler testExcelFile = new ExcelHandler(new File(excelFile.toURI()));
        testExcelFile.selectSheet("Data Sheet");
        HashMap<String, Cell> rowData = testExcelFile.mapTwoRows(2, 3, true);
        assertThat(rowData.size(), is(equalTo(1)));
        assertThat(rowData.get("cat").getContents(), is(equalTo("dog")));
    }

    public void getTwoRowsOfData() throws Exception {
        ExcelHandler testExcelFile = new ExcelHandler(new File(excelFile.toURI()));
        testExcelFile.selectSheet("Data Sheet");
        HashMap<String, Cell> rowData = testExcelFile.mapTwoRows(2, 3);
        assertThat(rowData.size(), is(equalTo(2)));
        assertThat(rowData.get("cat").getContents(), is(equalTo("dog")));
        assertThat(rowData.get("1").getContents(), is(equalTo("2")));
    }
}
