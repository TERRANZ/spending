package ru.terra.spending.controller;

import com.sun.jersey.api.core.HttpContext;
import org.apache.commons.lang.NotImplementedException;
import ru.terra.server.constants.ErrorConstants;
import ru.terra.server.controller.AbstractController;
import ru.terra.server.dto.ListDTO;
import ru.terra.server.dto.SimpleDataDTO;
import ru.terra.spending.constants.URLConstants;
import ru.terra.spending.db.entity.Transaction;
import ru.terra.spending.db.entity.User;
import ru.terra.spending.dto.TransactionDTO;
import ru.terra.spending.engine.TransactionsEngine;
import ru.terra.spending.engine.TypeEngine;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.Date;

@Path(URLConstants.MobileTransactions.MOBILE_TRANSACTIONS)
public class MobileTransactionController extends AbstractController<Transaction, TransactionDTO, TransactionsEngine> {
    private TypeEngine typeEngine = new TypeEngine();

    public MobileTransactionController() {
        super(TransactionsEngine.class, true, Transaction.class, TransactionDTO.class);
    }

    @POST
    @Path(URLConstants.MobileTransactions.DO_REGISTER)
    @Produces(MediaType.APPLICATION_JSON)
    public SimpleDataDTO<Integer> regTransactios(@Context HttpContext hc,
                                                 @FormParam(URLConstants.MobileTransactions.PARAM_TYPE) Integer type,
                                                 @FormParam(URLConstants.MobileTransactions.PARAM_MONEY) Double money,
                                                 @FormParam(URLConstants.MobileTransactions.PARAM_DATE) Long date) {

        if (engine == null)
            throw new NotImplementedException();
        if (!isAuthorized(hc)) {
            SimpleDataDTO<Integer> ret = new SimpleDataDTO<>(0);
            ret.errorCode = ErrorConstants.ERR_NOT_AUTHORIZED_ID;
            ret.errorMessage = ErrorConstants.ERR_NOT_AUTHORIZED_MSG;
            return ret;
        }
        try {
            Transaction transaction = new Transaction();
            transaction.setValue(money);
            transaction.setTrDate(new Date(date));
            transaction.setCreateDate(new Date());
            transaction.setUser((User) getCurrentUser(hc));
            transaction.setType(typeEngine.getBean(type));

            return new SimpleDataDTO<>(engine.createBean(transaction).getId());
        } catch (Exception e) {
            SimpleDataDTO<Integer> ret = new SimpleDataDTO<>(0);
            ret.errorMessage = e.getMessage();
            return ret;
        }
    }

    @GET
    @Path(URLConstants.MobileTransactions.DO_LIST_FROMDATE_JSON)
    public ListDTO<TransactionDTO> fromDate(@Context HttpContext hc, @QueryParam(URLConstants.MobileTransactions.PARAM_DATE) Long fromDate) {
        if (engine == null)
            throw new NotImplementedException();
        if (!isAuthorized(hc)) {
            ListDTO<TransactionDTO> ret = new ListDTO<>();
            ret.errorCode = ErrorConstants.ERR_NOT_AUTHORIZED_ID;
            ret.errorMessage = ErrorConstants.ERR_NOT_AUTHORIZED_MSG;
            return ret;
        }
        ListDTO<TransactionDTO> ret = new ListDTO<>();
        if (fromDate == null)
            fromDate = 0L;
        ret.setData(engine.getFromDate((User) getCurrentUser(hc), fromDate));
        return ret;
    }
}