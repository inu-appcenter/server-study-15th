# About Validation

## Validation?

��ȿ�� �˻�� ���ø����̼��� ����Ͻ� ������ �ùٸ��� �����ϱ� ���� �����͸� ������ �����ϴ� �۾�

## Validation in Spring boot

�������� Spring boot�� ������ �� spring-boot-starter-web�� ����  
Spring boot 2.3 ���� ������ ���̺귯�� (spring-boot-starter-validation) ����

Spring boot������ ��ȿ�� �˻�� �� �������� �����Ͱ� �Ѿ���� ������ �ǽ�  
���� �� ������ ������ �ϴ� ��ü = DTO(Data Transfer Object) ��ü�̹Ƿ�, DTO ��ü���� �����ϴ� ���� �Ϲ���

![������ �𵨿� ��ȿ�� �˻� ����](./img/validation_fig1.png)

### Validation Annotations

- ���ڿ� ����  
@Null : null ���� ���  

@NotNull : null�� ������� ���� ("", " "�� ���)  
@NotEmpty : null, ""�� ������� ���� (" "�� ���)  
@NotBlank : null, "", " " ��� ������� ����  

- �ִ�/�ּڰ� ���� (BigDecimal, BigInteger, int, long)  
@DemicalMin(value = "$numberString") : numberString���� ū �� ���  
@DemicalMax(value = "$numberString") : numberString���� ���� �� ���  

@Min(value = "$number") : $number �̻��� �� ���  
@Max(value = "$number") : $number ���� �� ���  

- ���� ���� ���� (BigDecimal, BigInteger, int, long)  
@Positive : ��� ���  
@Negative : ���� ���  

@PositiveOrZero : 0�� ������ ��� ���  
@NegativeOrZero : 0�� ������ ���� ���  

- �ð��� ���� ���� (Date, LocalDate, LocalDateTime)  
@Future : ���纸�� �̷��� ��¥ ���  
@Past : ���纸�� ������ ��¥ ���  

@FutureOrPresent : ���縦 ������ �̷��� ��¥ ���  
@PastOrPresent : ���縦 ������ ������ ��¥ ���  

- �̸��� ����  
@Email : �̸��� ������ �˻�, ""�� ��� 

- �ڸ��� ���� ���� (BigDecimal, BigInteger, int, long)  
@Digits(integer = $number1, fraction = $number2) : $number1�� ���� �ڸ����� $number2�� �Ҽ� �ڸ��� ���  

- Boolean ����  
@AssertTrue : true���� üũ, null�� üũ���� ����  
@AssertFalse : false���� üũ, null�� üũ���� ����  

- ���ڿ� ���� ����  
@Size(min = $number1, max = $number2) : $number1 �̻� $number2 ������ ���� ���  

- ���Խ� ����  
@Pattern(reqexp = "$expr") : ���Խ��� �˻� (���Խ��� java.util.regex.Pattern ��Ű���� ������)  

### Valid vs Validated