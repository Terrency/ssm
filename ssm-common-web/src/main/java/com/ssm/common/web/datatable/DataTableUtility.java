package com.ssm.common.web.datatable;

import com.ssm.common.base.page.Page;

public class DataTableUtility {

    public static <T> DataTableResponse<T> buildDataTable(DataTableRequest dataTableRequest, Page<T> page) {
        return new DataTableResponse<T>()
                .setDraw(dataTableRequest.getDraw())
                .setData(page.getItems())
                .setRecordsTotal(page.getTotalItems())
                .setRecordsFiltered(page.getTotalItems());
    }

}
