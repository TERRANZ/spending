package ru.terra.spending.controller;

import com.sun.jersey.api.core.HttpContext;
import org.apache.commons.lang.NotImplementedException;
import ru.terra.server.constants.ErrorConstants;
import ru.terra.server.controller.AbstractController;
import ru.terra.server.dto.SimpleDataDTO;
import ru.terra.spending.constants.URLConstants;
import ru.terra.spending.db.entity.Transaction;
import ru.terra.spending.db.entity.User;
import ru.terra.spending.dto.TransactionDTO;
import ru.terra.spending.engine.TransactionsEngine;
import ru.terra.spending.engine.TypeEngine;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.Date;

@Path(URLConstants.MobileTransactions.MOBILE_TRANSACTIONS)
public class MobileTransactionController extends AbstractController<Transaction, TransactionDTO, TransactionsEngine> {
    private TypeEngine typeEngine = new TypeEngine();

    public MobileTransactionController() {
        super(TransactionsEngine.class, true);
    }

    @GET
    @Path(URLConstants.MobileTransactions.DO_REGISTER)
    @Produces(MediaType.APPLICATION_JSON)
    public SimpleDataDTO<Boolean> regTransactios(@Context HttpContext hc,
                                                 @QueryParam(URLConstants.MobileTransactions.PARAM_TYPE) Integer type,
                                                 @QueryParam(URLConstants.MobileTransactions.PARAM_MONEY) Double money,
                                                 @QueryParam(URLConstants.MobileTransactions.PARAM_DATE) Long date) {

        if (engine == null)
            throw new NotImplementedException();
        if (!isAuthorized(hc)) {
            SimpleDataDTO<Boolean> ret = new SimpleDataDTO<Boolean>(false);
            ret.errorCode = ErrorConstants.ERR_NOT_AUTHORIZED_ID;
            ret.errorMessage = ErrorConstants.ERR_NOT_AUTHORIZED_MSG;
            return ret;
        }
        Transaction transaction = new Transaction();
        transaction.setValue(money);
        transaction.setTrDate(new Date(date));
        transaction.setCreateDate(new Date());
        transaction.setUser((User) getCurrentUser(hc));
        transaction.setType(typeEngine.getBean(type));

        engine.createBean(transaction);
        return new SimpleDataDTO<>(true);
    }
}