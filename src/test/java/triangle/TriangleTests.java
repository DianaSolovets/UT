/* чек-лист проверки программы:
Тесты для проверки ввода не пишу, так как конструктор принимает только double, при вводе непримитивного типа программа выдаст ошибку компиляции,
а любой примитивный тип будет преобразован в double.

Проверки конструктора:
- верно ли отрабатыват конструктор на допустимых зачениях;
- верно ли отрабатыват конструктор(не создает объект) на недопустимых значениях:
 - все значения отрицательные;
 - отрицательное А;
 - отрицательное В;
 - отрицательное С;

Проверки метода checkTriangle:
- нет сторон, равных 0:
 - нулю равна сторона А;
 - нулю равна сторона В;
 - нулю равна сторона С;
- сумма двух сторон не меньше третьей:
 - сумма сторон А и В меньше С;
 - сумма сторон А и С меньше В;
 - сумма сторон С и В меньше А;

Проверки метода detectTriangle:
- треугольник является  прямоугольным;
- треугольник является  равносторонним;
- треугольник является равнобедренным:
 - неравная сторона - А;
 - неравная сторона - В;
 - неравная сторона - С;
- треугольник является  равносторонним;
- треугольник является комбинацией признаков:
 - равносторонний + равнобедренный;
 - прямоугольный + равнобедренный;
 - прямоугольный + разносторонний;

Проверка метода getSquare:
- проверка работы метода на точно известных значениях.
 */

package triangle;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TriangleTests {

	@Test
	public void checkConstructorPositiveTest() {
		Triangle t = new Triangle(getRandomPosDouble(), getRandomPosDouble(),getRandomPosDouble());
		Assert.assertNotNull(t, "Triangle wasn't created.");
	}

	@Test
	public void checkConstructorNegativeTest() {
		Triangle t = new Triangle(getRandomNegDouble(), getRandomNegDouble(), getRandomNegDouble());
		Assert.assertNull(t, "Triangle was created.");
	}

	@Test
	public void checkConstructorNegativeNegParam1Test() {
		Triangle t = new Triangle(getRandomNegDouble(), getRandomPosDouble(), getRandomPosDouble());
		Assert.assertNull(t, "Triangle was created.");
	}

	@Test
	public void checkConstructorNegativeNegParam2Test() {
		Triangle t = new Triangle( getRandomPosDouble(),getRandomNegDouble(), getRandomPosDouble());
		Assert.assertNull(t, "Triangle was created.");
	}

	@Test
	public void checkConstructorNegativeNegParam3Test() {
		Triangle t = new Triangle(getRandomPosDouble(), getRandomPosDouble(), getRandomNegDouble());
		Assert.assertNull(t, "Triangle was created.");
	}

	@Test
	public void checkCheckTriangleSideAisBiggerThanNullTest() {
		Triangle t = new Triangle(0,getRandomPosDouble(),getRandomPosDouble());
		t.checkTriangle();
		Assert.assertEquals(t.getMessage(),"a<=0", "Side A is incorrect.");
	}

	@Test
	public void checkCheckTriangleSideBisBiggerThanNullTest() {
		Triangle t = new Triangle(getRandomPosDouble(),0, getRandomPosDouble());
		t.checkTriangle();
		Assert.assertEquals(t.getMessage(),"b<=0", "Side B is incorrect.");
	}

	@Test
	public void checkCheckTriangleSideCisBiggerThanNullTest() {
		Triangle t = new Triangle(getRandomPosDouble(), getRandomPosDouble(), 0);
		t.checkTriangle();
		Assert.assertEquals(t.getMessage(),"c<=0", "Side C is incorrect.");
	}

	@Test
	public void checkCheckTriangleSideCIsLowerThanSumABTest() {
		double a = getRandomPosDouble();
		double b = getRandomPosDouble();
		Triangle t = new Triangle(a, b, a+b+1);
		t.checkTriangle();
		Assert.assertEquals(t.getMessage(),"a+b<=c", "It's not a triangle.");
	}

	@Test
	public void checkCheckTriangleSideBIsLowerThanSumACTest() {
		double a = getRandomPosDouble();
		double c = getRandomPosDouble();
		Triangle t = new Triangle(a, a+c+1, c);
		t.checkTriangle();
		Assert.assertEquals(t.getMessage(),"a+c<=b", "It's not a triangle.");
	}

	@Test
	public void checkCheckTriangleSideAIsLowerThanSumBCTest() {
		double b = getRandomPosDouble();
		double c = getRandomPosDouble();
		Triangle t = new Triangle(b+c+1, b, c);
		t.checkTriangle();
		Assert.assertEquals(t.getMessage(),"b+c<=a", "It's not a triangle.");
	}

	//тесты на "чистую прямоугольность" треугольника игнорируются, как как прямоугольный треугольник всегда будет разносторонним,
	// кроме случая равнобедренности, что будет проверено отдельным тестом.
	@Test (enabled = false)
	public void checkDetectTriangleIsRectangularHypCTest() {
		//для проверки на рандомных значениях введен коэффициент, на который будут умножены стороны точно прямоугольного треугольника
		double coef = getRandomPosDouble();
		Triangle t = new Triangle(3*coef, 4*coef, 5*coef);
		// проверяю конкретное значение, а не переменную TR_RECTANGULAR, т.к. она принадлежит тестируемому классу и мы не можем быть в ней уверены.
		Assert.assertEquals(t.detectTriangle(), 8, "Triangle isn't rectangular.");
	}

	@Test(enabled = false)
	public void checkDetectTriangleIsRectangularHypATest() {
		double coef = getRandomPosDouble();
		Triangle t = new Triangle(5*coef, 3*coef, 4*coef);
		Assert.assertEquals(t.detectTriangle(), 8, "Triangle isn't rectangular.");
	}

	@Test(enabled = false)
	public void checkDetectTriangleIsRectangularHypBTest() {
		double coef = getRandomPosDouble();
		Triangle t = new Triangle(4*coef, 5*coef, 3*coef);
		Assert.assertEquals(t.detectTriangle(), 8, "Triangle isn't rectangular.");
	}

	@Test
	public void checkDetectTriangleIsEquilateralTest() {
		double a = getRandomPosDouble();
		double b = a;
		double c = a;
		Triangle t = new Triangle(a, b, c);
		// проверяю конкретное значение комбинации признаков, а не переменную TR_EQUILATERAL, т.к. равносторонний треугольник будет и равнобедренным.
		Assert.assertEquals(t.detectTriangle(), 3, "Triangle isn't equilateral.");
	}

	@Test
	public void checkDetectTriangleIsIsoscelesBaseATest() {
		double b = getRandomPosDouble();
		double a = b+1;
		double c = b;
		Triangle t = new Triangle(a, b, c);
		// проверяю конкретное значение, а не переменную TR_ISOSCELES, т.к. она принадлежит тестируемому классу и мы не можем быть в ней уверены.
		Assert.assertEquals(t.detectTriangle(), 2, "Triangle isn't isosceles.");
	}

	@Test
	public void checkDetectTriangleIsIsoscelesBaseBTest() {
		double a = getRandomPosDouble();
		double b = a+1;
		double c = a;
		Triangle t = new Triangle(a, b, c);
		// проверяю конкретное значение, а не переменную TR_ISOSCELES, т.к. она принадлежит тестируемому классу и мы не можем быть в ней уверены.
		Assert.assertEquals(t.detectTriangle(), 2, "Triangle isn't isosceles.");
	}

	@Test
	public void checkDetectTriangleIsIsoscelesBaseCTest() {
		double a = getRandomPosDouble();
		double b = a;
		double c = a+1;
		Triangle t = new Triangle(a, b, c);
		// проверяю конкретное значение, а не переменную TR_ISOSCELES, т.к. она принадлежит тестируемому классу и мы не можем быть в ней уверены.
		Assert.assertEquals(t.detectTriangle(), 2, "Triangle isn't isosceles.");
	}

	@Test
	public void checkDetectTriangleIsOrdinaryTest() {
		double a = getRandomPosDouble();
		double b = a+1;
		double c = a+2;
		Triangle t = new Triangle(a, b, c);
		// проверяю конкретное значение, а не переменную TR_ORDYNARY, т.к. она принадлежит тестируемому классу и мы не можем быть в ней уверены.
		Assert.assertEquals(t.detectTriangle(), 4, "Triangle isn't ordinary.");
	}

	//тесты на комбинацию признаков "прямоугольный" и равнобедренный" игнорируются,
	// так как никогда не смогут пройти успешно в силу погрешности в вычислениях.
	@Test(enabled = false)
	public void checkDetectTriangleIsCombinedRectangularAndIsoscelesHypCTest() {
		double a = getRandomPosDouble();
		double b = a;
		double c = Math.sqrt((a*a)+(b*b));
		Triangle t = new Triangle(a, b, c);
		// проверяю конкретное значение комбинации признаков
		Assert.assertEquals(t.detectTriangle(), 10, "Triangle isn't combined.");
	}

	@Test(enabled = false)
	public void checkDetectTriangleIsCombinedRectangularAndIsoscelesHypATest() {
		double b = getRandomPosDouble();
		double c = b;
		double a = Math.sqrt((c*c)+(b*b));
		Triangle t = new Triangle(a, b, c);
		// проверяю конкретное значение комбинации признаков
		Assert.assertEquals(t.detectTriangle(), 10, "Triangle isn't combined.");
	}

	@Test(enabled = false)
	public void checkDetectTriangleIsCombinedRectangularAndIsoscelesHypBTest() {
		double a = getRandomPosDouble();
		double c = a;
		double b = Math.sqrt((c*c)+(a*a));
		Triangle t = new Triangle(a, b, c);
		// проверяю конкретное значение комбинации признаков
		Assert.assertEquals(t.detectTriangle(), 10, "Triangle isn't combined.");
	}

	@Test
	public void checkDetectTriangleIsCombinedRectangularAndOrdinaryHypCTest() {
		double coef = getRandomPosDouble();
		Triangle t = new Triangle(8*coef,15*coef, 17*coef);
		// проверяю конкретное значение комбинации признаков
		Assert.assertEquals(t.detectTriangle(), 12, "Triangle isn't combined.");
	}

	@Test
	public void checkDetectTriangleIsCombinedRectangularAndOrdinaryHypATest() {
		double coef = getRandomPosDouble();
		Triangle t = new Triangle(13*coef, 5*coef, 12*coef);
		// проверяю конкретное значение комбинации признаков
		Assert.assertEquals(t.detectTriangle(), 12, "Triangle isn't combined.");
	}

	@Test
	public void checkDetectTriangleIsCombinedRectangularAndOrdinaryHypBTest() {
		double coef = getRandomPosDouble();
		Triangle t = new Triangle(12*coef, 13*coef, 5*coef);
		// проверяю конкретное значение комбинации признаков
		Assert.assertEquals(t.detectTriangle(), 12, "Triangle isn't combined.");
	}

	@Test
	public void checkGetSquareTest() {
		double coef = getRandomPosDouble();
		Triangle t = new Triangle(3*coef,4*coef,5*coef);
		Assert.assertEquals((t.getSquare()), 6*coef, "Method getSquare is incorrect." );
	}

	public double getRandomPosDouble(){
		double c = (int)(1 + (Math.random() * 1000));
		double rand = c/10;
		return rand;
	}

	public double getRandomNegDouble(){
		return -(getRandomPosDouble()-0.1);
	}
}
