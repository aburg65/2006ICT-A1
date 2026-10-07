package com.tetris;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.Socket;

public class TetrisClient {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 3000;

    public OpMove getMove(PureGame game) {

        ObjectMapper mapper = new ObjectMapper();

        try (
                Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
                PrintWriter out = new PrintWriter(
                        new OutputStreamWriter(socket.getOutputStream()), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()))
        ) {

            String jsonGameState = mapper.writeValueAsString(game);
            System.out.println(jsonGameState);
            out.println(jsonGameState);
            String response = in.readLine();
            OpMove move = mapper.readValue(response, OpMove.class);

            System.out.println(
                    "Server move: X=" + move.opX()
                            + ", rotations=" + move.opRotate()
            );
            return move;

        } catch (IOException e) {

            System.out.println("Could not connect to TetrisServer: "
                    + e.getMessage());

            return null;
        }
    }
}