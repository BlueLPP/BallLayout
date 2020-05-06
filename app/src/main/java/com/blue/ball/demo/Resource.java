package com.blue.ball.demo;

import java.util.ArrayList;
import java.util.List;

class Resource {

    private static final int[] RESOURCES = new int[]{
            R.drawable.picture0, R.drawable.picture1, R.drawable.picture2, R.drawable.picture3, R.drawable.picture4,
            R.drawable.picture5, R.drawable.picture6, R.drawable.picture7, R.drawable.picture8, R.drawable.picture9,
            R.drawable.picture10, R.drawable.picture11, R.drawable.picture12, R.drawable.picture13, R.drawable.picture14,
            R.drawable.picture15, R.drawable.picture16, R.drawable.picture17, R.drawable.picture18, R.drawable.picture19,
            R.drawable.picture20, R.drawable.picture21, R.drawable.picture22, R.drawable.picture23, R.drawable.picture24,
            R.drawable.picture25, R.drawable.picture26, R.drawable.picture27, R.drawable.picture28, R.drawable.picture29,
            R.drawable.picture30, R.drawable.picture31, R.drawable.picture32, R.drawable.picture33, R.drawable.picture34,
            R.drawable.picture35, R.drawable.picture36, R.drawable.picture37, R.drawable.picture38, R.drawable.picture39,
            R.drawable.picture40, R.drawable.picture41, R.drawable.picture42, R.drawable.picture43, R.drawable.picture44,
            R.drawable.picture45, R.drawable.picture46, R.drawable.picture47, R.drawable.picture48, R.drawable.picture49,
            R.drawable.picture50, R.drawable.picture51, R.drawable.picture52, R.drawable.picture53, R.drawable.picture54,
            R.drawable.picture55, R.drawable.picture56, R.drawable.picture57, R.drawable.picture58, R.drawable.picture59,
            R.drawable.picture60, R.drawable.picture61, R.drawable.picture62, R.drawable.picture63, R.drawable.picture64,
            R.drawable.picture65, R.drawable.picture66, R.drawable.picture67, R.drawable.picture68, R.drawable.picture69,
            R.drawable.picture70, R.drawable.picture71, R.drawable.picture72, R.drawable.picture73, R.drawable.picture74,
            R.drawable.picture75, R.drawable.picture76, R.drawable.picture77, R.drawable.picture78, R.drawable.picture79,
            R.drawable.picture80, R.drawable.picture81, R.drawable.picture82, R.drawable.picture83, R.drawable.picture84,
            R.drawable.picture85, R.drawable.picture86, R.drawable.picture87, R.drawable.picture88, R.drawable.picture89,
            R.drawable.picture90, R.drawable.picture91, R.drawable.picture92, R.drawable.picture93, R.drawable.picture94,
            R.drawable.picture95, R.drawable.picture96, R.drawable.picture97, R.drawable.picture98, R.drawable.picture99,
            R.drawable.picture100, R.drawable.picture101, R.drawable.picture102, R.drawable.picture103, R.drawable.picture104,
            R.drawable.picture105, R.drawable.picture106, R.drawable.picture107, R.drawable.picture108, R.drawable.picture109,
            R.drawable.picture110, R.drawable.picture111, R.drawable.picture112, R.drawable.picture113, R.drawable.picture114,
            R.drawable.picture115, R.drawable.picture116, R.drawable.picture117, R.drawable.picture118, R.drawable.picture119,
            R.drawable.picture120, R.drawable.picture121, R.drawable.picture122, R.drawable.picture123, R.drawable.picture124,
            R.drawable.picture125, R.drawable.picture126, R.drawable.picture127, R.drawable.picture128, R.drawable.picture129,
            R.drawable.picture130, R.drawable.picture131, R.drawable.picture132, R.drawable.picture133, R.drawable.picture134,
            R.drawable.picture135, R.drawable.picture136, R.drawable.picture137, R.drawable.picture138, R.drawable.picture139,
            R.drawable.picture140, R.drawable.picture141, R.drawable.picture142, R.drawable.picture143, R.drawable.picture144,
            R.drawable.picture145, R.drawable.picture146, R.drawable.picture147, R.drawable.picture148, R.drawable.picture149,
            R.drawable.picture150, R.drawable.picture151, R.drawable.picture152, R.drawable.picture153, R.drawable.picture154,
            R.drawable.picture155, R.drawable.picture156, R.drawable.picture157, R.drawable.picture158, R.drawable.picture159,
            R.drawable.picture160, R.drawable.picture161, R.drawable.picture162, R.drawable.picture163, R.drawable.picture164,
            R.drawable.picture165, R.drawable.picture166, R.drawable.picture167, R.drawable.picture168, R.drawable.picture169,
            R.drawable.picture170, R.drawable.picture171, R.drawable.picture172, R.drawable.picture173, R.drawable.picture174,
            R.drawable.picture175, R.drawable.picture176, R.drawable.picture177, R.drawable.picture178, R.drawable.picture179,
            R.drawable.picture180, R.drawable.picture181, R.drawable.picture182, R.drawable.picture183, R.drawable.picture184,
            R.drawable.picture185, R.drawable.picture186, R.drawable.picture187, R.drawable.picture188, R.drawable.picture189,
            R.drawable.picture190, R.drawable.picture191, R.drawable.picture192, R.drawable.picture193, R.drawable.picture194,
            R.drawable.picture195, R.drawable.picture196, R.drawable.picture197, R.drawable.picture198, R.drawable.picture199,
            R.drawable.picture200, R.drawable.picture201, R.drawable.picture202, R.drawable.picture203, R.drawable.picture204,
            R.drawable.picture205, R.drawable.picture206, R.drawable.picture207, R.drawable.picture208, R.drawable.picture209,
            R.drawable.picture210, R.drawable.picture211, R.drawable.picture212, R.drawable.picture213, R.drawable.picture214,
            R.drawable.picture215, R.drawable.picture216, R.drawable.picture217, R.drawable.picture218, R.drawable.picture219,
            R.drawable.picture220, R.drawable.picture221, R.drawable.picture222, R.drawable.picture223, R.drawable.picture224,
            R.drawable.picture225, R.drawable.picture226, R.drawable.picture227, R.drawable.picture228, R.drawable.picture229,
            R.drawable.picture230, R.drawable.picture231, R.drawable.picture232, R.drawable.picture233, R.drawable.picture234,
            R.drawable.picture235, R.drawable.picture236, R.drawable.picture237, R.drawable.picture238, R.drawable.picture239,
            R.drawable.picture240, R.drawable.picture241, R.drawable.picture242, R.drawable.picture243, R.drawable.picture244,
            R.drawable.picture245, R.drawable.picture246, R.drawable.picture247, R.drawable.picture248, R.drawable.picture249,
            R.drawable.picture250, R.drawable.picture251, R.drawable.picture252, R.drawable.picture253, R.drawable.picture254,
            R.drawable.picture255, R.drawable.picture256, R.drawable.picture257, R.drawable.picture258, R.drawable.picture259,
            R.drawable.picture260, R.drawable.picture261, R.drawable.picture262, R.drawable.picture263, R.drawable.picture264,
            R.drawable.picture265, R.drawable.picture266, R.drawable.picture267, R.drawable.picture268, R.drawable.picture269,
            R.drawable.picture270, R.drawable.picture271, R.drawable.picture272, R.drawable.picture273, R.drawable.picture274,
            R.drawable.picture275, R.drawable.picture276, R.drawable.picture277, R.drawable.picture278, R.drawable.picture279,
            R.drawable.picture280, R.drawable.picture281, R.drawable.picture282, R.drawable.picture283, R.drawable.picture284,
            R.drawable.picture285, R.drawable.picture286, R.drawable.picture287, R.drawable.picture288, R.drawable.picture289,
            R.drawable.picture290, R.drawable.picture291, R.drawable.picture292, R.drawable.picture293, R.drawable.picture294,
            R.drawable.picture295, R.drawable.picture296, R.drawable.picture297, R.drawable.picture298, R.drawable.picture299};

    static List<Integer> getResources(int count) {
        if (count >= RESOURCES.length || count <= 0) {
            return arrayToList(RESOURCES.length);
        } else {
            return arrayToList(count);
        }
    }

    static List<Integer> getResources() {
        return arrayToList(RESOURCES.length);
    }

    private static List<Integer> arrayToList(int length) {
        List<Integer> list = new ArrayList<>(length);
        for (int res : RESOURCES) {
            list.add(res);
        }
        return list;
    }
}
