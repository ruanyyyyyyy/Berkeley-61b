import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    private ArrayList<Double> LonDPPdep = new ArrayList<Double>();
    private double width, height;
    private int tile_size;

    public Rasterer() {
        // YOUR CODE HERE
        width = MapServer.ROOT_LRLON - MapServer.ROOT_ULLON;
        height = MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT;
        tile_size = MapServer.TILE_SIZE;
        double temp = width / (tile_size * 2);
        for (int i = 0; i < 7; i += 1) {
            LonDPPdep.add(temp);
            temp /= 2;
        }
        //LonDPPdep.get(0) = 0.000171661376953125(depth = 1)
        //LonDPPdep.get(1) = 0.0000858306884765625(depth = 2)
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        System.out.println(params);
        Map<String, Object> results = new HashMap<>();
        // System.out.println("Since you haven't implemented getMapRaster, nothing is displayed in "
                           // + "your browser.");
        double lrlon = params.get("lrlon");
        double ullon = params.get("ullon");
        double w = params.get("w");
        double h = params.get("h");
        double ullat = params.get("ullat");
        double lrlat = params.get("lrlat");

        // find the appropriate depth
        double LonDPP = (lrlon - ullon) / w;
        int depth = 0;
        for (int i = 0; i < LonDPPdep.size(); i += 1) {
            depth = i;
            if (LonDPPdep.get(i) <= LonDPP) {
                break;
            }
        }
        infos(7, 5, 18);
        //hello world思想，从最简单的开始
        //难点：每一层左上角坐标附近的tile name
        return results;
    }

    /* calculate this tile's information => ullon, ullat, lrlon, lrlat, distance per pixel  */
    /* haven't tested this function!! */
    private void infos(int dep, int x, int y) {
        double widNum = width / Math.pow(2, dep);
        double ullon = MapServer.ROOT_ULLON + widNum * x;
        double lrlon = MapServer.ROOT_LRLON + widNum * x;
        double heightNum = height / Math.pow(2, dep);
        double ullat= MapServer.ROOT_ULLAT - heightNum * y;
        double lrlat = MapServer.ROOT_LRLAT - heightNum * y;

    }

}
