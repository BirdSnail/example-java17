package birdsnail.example.base;

public class 泛型 {

    public static void main(String[] args) {
        // SKU配置:左侧SKU项
//        List<GoodsSkuAttrEntity> goodsSkuAttrEntityList = new ArrayList<>();
//        List<GoodsSkuAttrBO> goodsSkuAttrBOList = map(goodsSkuAttrEntityList, GoodsSkuAttrBO::new);
    }

//    public static <T, R> List<R> map(List<T> in, Supplier<R> target) {
//        return in.stream()
//                .map(it -> {
//                    R r = target.get();
//                    try {
//                        BeanUtils.copyProperties(it, r);
//                    } catch (IllegalAccessException | InvocationTargetException e) {
//                        e.printStackTrace();
//                    }
//                    return r;
//                })
//                .collect(Collectors.toList());
//    }


    private static class GoodsSkuAttrEntity {
    }

    private static class GoodsSkuAttrBO {
    }
}
