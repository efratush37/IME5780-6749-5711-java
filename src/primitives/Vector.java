package primitives;

import java.util.Objects;
public class Vector {
    //field
    private Point3D head;

    /**
     * constructor with three arguments
     * @param _c1= coordinate
     * @param _c2= coordinate
     * @param _c3= coordinate
     */
    public Vector(Coordinate _c1, Coordinate _c2, Coordinate _c3) {
        Point3D temp = new Point3D(_c1, _c2, _c3);
        isZero(temp);
        head = temp;
    }

    /**
     * constructor with three arguments
     * @param d1= double value
     * @param d2= double value
     * @param d3= double value
     */
    public Vector(double d1, double d2, double d3) {
        Point3D temp = new Point3D(d1, d2, d3);
        isZero(temp);
        this.head = temp;
    }

    /**
     * constructor with one argument
     * @param p= point of the vector(head)
     */
    public Vector(Point3D p) {
        isZero(p);
        this.head = new Point3D(p);
    }

    /**
     * copy constructor
     * @param v an existing vector
     */
    public Vector(Vector v) {
        head = new Point3D(v.head);
    }

    /**
     * get method for the head point field
     * @return the value of the head point field
     */
    public Point3D getHead() {
        return head;
    }

    /**
     * implement equal method(comparing)
     * @param o Object
     * @return true or false value regarding their equality
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return Objects.equals(head, vector.head);
    }

    /** for our comfortable
     * check if its the zero point
     * @param p point to check
     */
    public static void isZero(Point3D p){
        if (p.equals(Point3D.getZERO())) {
            throw new IllegalArgumentException("can not set zero vector");
        }
    }

    /**
     * implement to string method
     * @return string describes the object
     */
    @Override
    public String toString() {
        return "Vector{" +
                "head=" + head +
                '}';
    }

    /**
     * this function implements subtraction between two vectors
     * @param v= vector
     * @return the result of the subtraction as a vector
     */
    public Vector subtract(Vector v) {
        return new Vector((this.head).subtract(v.head));
    }

    /**
     * this function implements addition between two vectors
     * @param v= vector
     * @return the result of the addition as a vector
     */
    public Vector add(Vector v) {
        return new Vector(this.head.add(v));
    }

    /**
     * this function implements multiplication of a vector in scalar
     * @param s= scalar
     * @return= the result as a vector
     */
    public Vector scale(double s) {
        return new Vector(this.head.getC1().get() * s,
                this.head.getC2().get() * s,
                this.head.getC3().get() * s);
    }

    /**
     * this function calculates the length squared of a vector
     * @return the length squared
     */
    public double lengthSquared() {
        double x = this.head.getC1().get();
        double y = this.head.getC2().get();
        double z = this.head.getC3().get();
        return (x * x) + (y * y) + (z * z);
    }

    /**
     * this function calculates the length of a vector
     * @return the length
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * this function calculates the value of a dot product of two vectors
     * @param v= vector
     * @return the value of a dot product
     */
    public double dotProduct(Vector v) {
        double x = this.head.getC1().get() * v.head.getC1().get();
        double y = this.head.getC2().get() * v.head.getC2().get();
        double z = this.head.getC3().get() * v.head.getC3().get();
        return x + y + z;
    }

    /**
     * this function calculates the value of a cross product of two vectors
     * @param v= vector
     * @return the value of a cross product
     */
    public Vector crossProduct(Vector v) {
        return new Vector((this.head.getC2().get() * v.head.getC3().get()) - (this.head.getC3().get() * v.head.getC2().get()),
                (this.head.getC3().get() * v.head.getC1().get()) - (this.head.getC1().get() * v.head.getC3().get()),
                (this.head.getC1().get() * v.head.getC2().get()) - (this.head.getC2().get() * v.head.getC1().get()));
    }

    /**
     * this function normalize a vector
     * @return the vector after normalize
     */
    public Vector normalize() {
        double size = length();
        this.head = new Point3D(this.getHead().getC1().get() / size,
                this.head.getC2().get() / size,
                this.head.getC3().get() / size);
        return this;
    }

    /**
     * this function normalize a vector
     * @return the normalize vector
     */
    public Vector normalized() {
        return new Vector(this.normalize());
    }
}

















