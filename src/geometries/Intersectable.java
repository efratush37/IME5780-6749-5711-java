package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;
import java.util.Objects;

/**
 * Geometries.Intersectable interface
 * @author Rivka Zizovi 207265711 and Efrat Anconina 322796749
 * this interface represents the ability of geometries of being Intersectable
 */
public interface Intersectable {
    /**
     * this function calculate the intersections points
     * (refactoring, returns list of geo points instead of regular points)
     * @param ray the ray thrown toward the geometry
     * @return list of geo points created by the intersection between the ray and the geometry
     */
    List<GeoPoint> findIntsersections(Ray ray);

    /**
     * this function returns the box of the intersectable object
     * @return th box
     */
    Box getBox();

    /**
     * this function return rather the box is intersected or not
     * @param ray the constructed ray
     * @return rather the box intersected or not
     */
    boolean IntersectedBox(Ray ray);

    /**
     * an inner Geo Point class which represent an intersected point by both the value of the point
     * and the kind of geometry has been intersected
     * refactoring, instead of returning point3d as intersected point
     */
    public static class GeoPoint {
        //fields
        public Geometry geometry;
        public Point3D point;


        /**
         * a constructor for the geo point, which gets two arguments, the point and the geometry
         * @param geometry the geometry has been intersected
         * @param point the point where the geometry has been intersected
         */
        public GeoPoint(Geometry geometry, Point3D point) {
            this.geometry = geometry;
            this.point = point;
        }

        /**
         * get method for the point field
         * @return the value of the point field
         */
        public Point3D getPoint() {
            return point;
        }

        /**
         * get method for the geometry field
         * @return the value of the geometry field
         */
        public Geometry getGeometry() {
            return geometry;
        }

        /**
         * this function implements the equals method(comparing)
         * @param o an object who's going to be comparing
         * @return rether the two arguments has been compered are equal or not
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || !(o instanceof GeoPoint)) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return this.geometry.equals(geoPoint.geometry) &&
                    this.point.equals(geoPoint.point);
        }
    }

    public static class Box{
        //fields
        double x1;
        double x2;
        double y1;
        double y2;
        double z1;
        double z2;

        /**
         * constructor for the box
         * @param x1 value fot the point create the box
         * @param x2 value fot the point create the box
         * @param y1 value fot the point create the box
         * @param y2 value fot the point create the box
         * @param z1 value fot the point create the box
         * @param z2 value fot the point create the box
         */
        public Box(double x1, double x2, double y1, double y2, double z1, double z2){
            this.x1= x1;
            this.x2= x2;
            this.y1= y1;
            this.y2= y2;
            this.z1= z1;
            this.z2= z2;
        }

        //get methods for all the fields
        public double getX1() {
            return x1;
        }

        public double getX2() {
            return x2;
        }

        public double getY1() {
            return y1;
        }

        public double getY2() {
            return y2;
        }

        public double getZ1() {
            return z1;
        }

        public double getZ2() {
            return z2;
        }

        /**
         * this function returns rather the box intersected with the ray or not
         * @param ray the ray intersected to the scene
         * @return rather the box intersected with the ray or not
         */
        public boolean IntersectedBox(Ray ray){
            Point3D p= ray.get_p0();
            Vector d= ray.getDir();
            double TminX, TmaxX, TminY, TmaxY, TminZ, TmaxZ, Tmin, Tmax;
            //To find where a ray intersects
            double Tx1= (x1- p.getC1().get())/ d.getHead().getC1().get();
            double Tx2= (x2- p.getC1().get())/ d.getHead().getC1().get();
            //To find what solution is really an intersection with the box, you require the greater value of the t parameter for the intersection at the min plane.
            if(Tx1>Tx2){
                TmaxX= Tx1;
                TminX= Tx2;
            }
            else{
                TmaxX= Tx2;
                TminX= Tx1;
            }

            double Ty1= (y1- p.getC2().get())/ d.getHead().getC2().get();
            double Ty2= (y2- p.getC2().get())/ d.getHead().getC2().get();
            if(Ty1>Ty2){
                TmaxY= Ty1;
                TminY= Ty2;
            }
            else{
                TmaxY= Ty2;
                TminY= Ty1;
            }

            if(TminX > TmaxY || TminY >TmaxX)
                return false;
            Tmin= (TminX<TminY? TminX: TminY);
            Tmax= (TmaxX>TmaxY? TmaxX: TmaxY);

            double Tz1= (z1- p.getC3().get())/ d.getHead().getC3().get();
            double Tz2= (z2- p.getC3().get())/ d.getHead().getC3().get();
            if(Tz1>Tz2){
                TmaxZ= Tz1;
                TminZ= Tz2;
            }
            else{
                TmaxZ= Tz2;
                TminZ= Tz1;
            }

            if(TminZ>Tmax || Tmin>TmaxZ)
                return false;
            Tmin= (TminZ<Tmin? TminZ: Tmin);
            Tmax= (TmaxZ>Tmax? TmaxZ: Tmax);

            return true;
        }
    }
}
