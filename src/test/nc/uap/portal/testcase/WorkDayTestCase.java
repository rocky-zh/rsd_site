package nc.uap.portal.testcase;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;
import nc.bs.framework.test.AbstractTestCase;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.portal.comm.WorkDayHelper;
import nc.vo.pub.lang.UFDate;
import nc.vo.uap.distribution.DateUtils;

import org.apache.commons.lang.ArrayUtils;

/**
 * ������ɸѡ��������
 * 
 * @author licza
 * 
 */
public class WorkDayTestCase extends AbstractTestCase {
	public void testNextDay(){
		UFDate s1 = new UFDate();
		Date d1 = WorkDayHelper.newIns().getNextDate(s1.getMillis(), 10);
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
		assertEquals("11-03-14",sdf.format(d1));
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * �����չ�������
	 */
	private static final IWorkDayFilterChain[] chains = new IWorkDayFilterChain[] 
    {
		new SpecialWorkDayFilterChain(),
		new HolidayFilterChain(),
		new WeekendFilterChain()
	};
	
	
	
	public void _testFilterWeeken() {
		final int n = 2;
		long c = System.currentTimeMillis();
		ProvingDate pd = new ProvingDate();
		int j = 1;
		while (pd.getIdentifier() <= n) {
			pd.setTimestamp(DateUtils.addDays(c, j));
			rock(pd);
			j++;
		}
		Date td = new Date(pd.getTtimestamp());
		System.out.println(DateUtils.getDate(td));
	}

	/**
	 * ���������
	 * @param pd
	 */
	public void rock(ProvingDate pd) {
		WorkDayFilterChain chainBase = new WorkDayFilterChain();
		for (int k = 0; k < chains.length; k++) {
			/**
			 * �����Ͽ�������
			 */
			if(!chainBase.isChainBreak())
				chains[k].doFilter(pd, chainBase);
		}
	}

	/**
	 * ����ʱ��
	 * 
	 * @param startDay
	 * @param stopDay
	 * @param t
	 * @return
	 */
	public static boolean among(long startDay, long stopDay, long t) {
		return t >= DateUtils.startOfDayInMillis(startDay) && t <= DateUtils.endOfDayInMillis(stopDay);
	}

	/**
	 * ��һ���������
	 * 
	 * @return
	 */
	public List<long[]> mockDate() {
		try {
			parseDate("", new String[] { "yyyy-MM-dd" });
		} catch (ParseException e) {
			LfwLogger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * ���ݶ���������������ַ���
	 * 
	 * @param str
	 * @param parsePatterns
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDate(String str, String[] parsePatterns)
			throws ParseException {
		if (str == null || parsePatterns == null) {
			throw new IllegalArgumentException("���ڽ�������Ϊ��!");
		}
		SimpleDateFormat parser = null;
		ParsePosition pos = new ParsePosition(0);
		for (int i = 0; i < parsePatterns.length; i++) {
			if (i == 0) {
				parser = new SimpleDateFormat(parsePatterns[0]);
			} else {
				parser.applyPattern(parsePatterns[i]);
			}
			pos.setIndex(0);
			Date date = parser.parse(str, pos);
			if (date != null && pos.getIndex() == str.length()) {
				return date;
			}
		}
		throw new ParseException("Unable to parse the date: " + str, -1);
	}
}

/**
 * Ҫ�����ʱ��
 * 
 * @author licza
 * 
 */
class ProvingDate {
	/**
	 * ��ǰ������
	 */
	private int identifier = 1;
	/**
	 * ����ʱ���
	 */
	private Long timestamp;

	public int getIdentifier() {
		return identifier;
	}

	public void setIdentifier(int i) {
		this.identifier = i;
	}

	public Long getTtimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long t) {
		this.timestamp = t;
	}
}

/**
 * �����չ������ӿ�
 * 
 * @author licza
 * 
 */
interface IWorkDayFilterChain {
	/**
	 * �����Ƿ���Ϲ���
	 * 
	 * @param t
	 *            ���������
	 * @param i
	 *            ����
	 * @param filterChain
	 *            ��һ����
	 */
	public void doFilter(ProvingDate pd, WorkDayFilterChain filterChain);
}

/**
 * �����չ�����
 * 
 * @author licza
 * 
 */
class WorkDayFilterChain {
	/** ���Ƿ�Ͽ� **/
	private boolean breakChain = false;
	public void doFilter(ProvingDate pd) {
		pd.setIdentifier(pd.getIdentifier() + 1);
		breakChain();
	}
	/**
	 * �������Ƿ�Ͽ�
	 * @return
	 */
	public boolean isChainBreak(){
		return breakChain;
	}
	/**
	 * �Ͽ��� 
	 **/
	public void breakChain(){
		breakChain = true;
	}
}

/**
 * ��ĩ������
 * 
 * @author licza
 * 
 */
class WeekendFilterChain implements IWorkDayFilterChain {

	@Override
	public void doFilter(ProvingDate pd, WorkDayFilterChain filterChain) {
		int[] weekend = new int[] { 1, 7 };
		int wk = DateUtils.getDayOfWeek(pd.getTtimestamp());
		if (!ArrayUtils.contains(weekend, wk))
			filterChain.doFilter(pd);
	}
}

/**
 * ���⹤���չ�����
 * 
 * @author licza
 * 
 */
class SpecialWorkDayFilterChain implements IWorkDayFilterChain {
	@Override
	public void doFilter(ProvingDate pd, WorkDayFilterChain filterChain) {
		try {
			long startDay = WorkDayTestCase.parseDate("2011-02-27", new String[] { "yyyy-MM-dd" }).getTime();
			long stopDay = WorkDayTestCase.parseDate("2011-02-27", new String[] { "yyyy-MM-dd" }).getTime();
			boolean b = WorkDayTestCase.among(startDay, stopDay, pd.getTtimestamp());
			if(b)
				filterChain.doFilter(pd);
		} catch (Exception e) {
			
		}
	}
}

/**
 * ���ڹ�����
 * 
 * @author licza
 * 
 */
class HolidayFilterChain implements IWorkDayFilterChain {
	@Override
	public void doFilter(ProvingDate pd, WorkDayFilterChain filterChain) {
		try {
			long startDay = WorkDayTestCase.parseDate("2011-02-28", new String[] { "yyyy-MM-dd" }).getTime();
			long stopDay = WorkDayTestCase.parseDate("2011-03-11", new String[] { "yyyy-MM-dd" }).getTime();
			boolean b = WorkDayTestCase.among(startDay, stopDay, pd.getTtimestamp());
			/**
			 * ����Ǽ��� ����������
			 */
			if(b)
				filterChain.breakChain();
		} catch (Exception e) {
			
		}
	}
}
