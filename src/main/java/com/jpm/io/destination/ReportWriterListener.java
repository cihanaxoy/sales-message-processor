package com.jpm.io.destination;

import com.jpm.model.Message;
import com.jpm.model.ProductType;
import com.jpm.model.Sale;

import java.util.List;
import java.util.Map;

/**
 *  This interface is to specify message service listeners for reporting purposes
 */
public interface ReportWriterListener {

    void writeSalesLogs(Map<ProductType, List<Sale>> saleLogs);
    void writeAdjustmentLogs(Map<ProductType, Map<Integer, Message>> adjustmentLogs);
    void writeNotification(String message);
}
