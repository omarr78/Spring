
<img width="850" height="425" alt="AOP drawio" src="https://github.com/user-attachments/assets/2b4f1f90-68e2-4d25-a1b8-74df94c04f43" />

## What is Aspect Oriented Programming?
Typically most of the applications use layered approach.

**What is a layered approach?**

You have a number of layers like this:

<img width="850" height="280" alt="layers drawio" src="https://github.com/user-attachments/assets/961b4198-d193-432f-8232-bff37412f07d" />

---

## A layered approach is typically used to build applications:

* **Web Layer** – View logic for web apps OR JSON conversion for REST API
* **Business Layer** – Business Logic
* **Data Layer** – Persistence Logic

--- 

### Each layer has different responsibilities
**HOWEVER**, there are a few common aspects that apply to **all layers** like:
* Security
* Performance
* Logging
* ...

These common aspects are called:

### **Cross Cutting Concerns**

---

## Now, how do you implement cross cutting concerns?

You don't wanna go into each layer and implement code for it. That will result in:

* A lot of duplication of code
* Difficult maintenance

That’s why we make use of:

### **Aspect Oriented Programming**

**Aspect Oriented Programming** can be used to implement Cross Cutting Concerns.

---

## So what we will do in AOP to implement cross cutting concerns:

### 1. Implement the cross cutting concern as an **aspect** (as a separate code)

### 2. Define **point cuts** to indicate where the aspect should be applied

---

> **Example:**
> So you would have a logging aspect and you can say:
> I would want to apply logging aspect on **business layer**, **data layer**, or **web layer**.

So you'd implement some common logic called an **aspect**
and you can define something called a **point cut**
to show where it should be applied.

---

## If you want to implement AOP — what are the options that you have?


## TWO Popular AOP Frameworks

### 1. Spring AOP

* **NOT** a complete AOP solution BUT very popular
* Only works with **Spring Beans**
* **Example:** Intercept method calls to Spring Beans
  (Before a specific method is called on a Spring Bean, you can intercept that and execute some logic)

---

### 2. AspectJ

* Complete AOP solution BUT rarely used
* **Example:** Intercept any method call on any Java class
  (It does **not** have to be a Spring Bean)
* **Example:** Intercept change of values in a field
  (So if you have a class and inside the class you have a member variable,
  you can even intercept changes in values for that member variable)

---
