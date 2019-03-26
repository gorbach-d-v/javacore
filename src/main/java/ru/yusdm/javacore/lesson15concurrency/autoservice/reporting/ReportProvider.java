package ru.yusdm.javacore.lesson15concurrency.autoservice.reporting;

import ru.yusdm.javacore.lesson15concurrency.autoservice.mark.service.MarkService;
import ru.yusdm.javacore.lesson15concurrency.autoservice.model.service.ModelService;
import ru.yusdm.javacore.lesson15concurrency.autoservice.order.service.OrderService;
import ru.yusdm.javacore.lesson15concurrency.autoservice.user.service.UserService;

import java.io.File;

public class ReportProvider {

    private final UserService userService;
    private final OrderService orderService;
    private final MarkService markService;
    private final ModelService modelService;

    private ReportComponent userOrdersTextFileReport;

    public ReportProvider(UserService userService, OrderService orderService,
                          MarkService markService, ModelService modelService) {
        this.userService = userService;
        this.orderService = orderService;
        this.markService = markService;
        this.modelService = modelService;

        initReportComponents();
    }

    private void initReportComponents() {
        userOrdersTextFileReport = new UserOrdersIoTextFileReport(
                userService,
                orderService,
                markService,
                modelService);
    }

    public File getUserOrdersTextFileReport() throws Exception {
        return userOrdersTextFileReport.generateReport();
    }

}
