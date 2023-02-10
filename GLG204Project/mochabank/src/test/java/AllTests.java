
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.anotherbank.mokabank.authentication.domain.dao.BankClerkDAOTest;
import com.anotherbank.mokabank.authentication.domain.dao.CustomerDAOTest;
import com.anotherbank.mokabank.authentication.domain.dao.ProspectCustomerDAOTest;
import com.anotherbank.mokabank.authentication.domain.model.UserTest;
import com.anotherbank.mokabank.authentication.domain.service.BankClerkServiceTest;
import com.anotherbank.mokabank.authentication.domain.service.CustomerServiceTest;
import com.anotherbank.mokabank.authentication.domain.service.ProspectCustomerServiceTest;
import com.anotherbank.mokabank.domain.dao.AccountDAOTest;
import com.anotherbank.mokabank.domain.dao.OperationDAOTest;
import com.anotherbank.mokabank.domain.dao.ScheduledOperationDAOTest;
import com.anotherbank.mokabank.domain.model.AccountTest;
import com.anotherbank.mokabank.domain.model.OperationTest;
import com.anotherbank.mokabank.domain.model.ScheduledOperationTest;
import com.anotherbank.mokabank.domain.service.AccountServiceTest;
import com.anotherbank.mokabank.domain.service.OperationServiceTest;
import com.anotherbank.mokabank.domain.service.ScheduledOperationServiceTest;
import com.anotherbank.mokabank.web.WebTestMockMvc;

import junit.framework.JUnit4TestAdapter;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	UserTest.class,
	CustomerDAOTest.class,
	ProspectCustomerDAOTest.class,
	BankClerkDAOTest.class,
	AccountTest.class,
	AccountDAOTest.class,
	OperationTest.class,
	OperationDAOTest.class,
	ScheduledOperationTest.class,
	ScheduledOperationDAOTest.class,
	CustomerServiceTest.class,
	ProspectCustomerServiceTest.class,
	BankClerkServiceTest.class,
	AccountServiceTest.class,
	OperationServiceTest.class,
	ScheduledOperationServiceTest.class,
	WebTestMockMvc.class
})

public class AllTests {
	
	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
		// org.junit.runner.JUnitCore.main("AllTests");
	}

	public static junit.framework.Test suite() {
		JUnit4TestAdapter adapter = new JUnit4TestAdapter(AllTests.class);
		return adapter;
	}
}

