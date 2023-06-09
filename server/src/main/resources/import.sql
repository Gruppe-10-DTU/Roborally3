insert into GAME (CURRENT_PLAYERS, MAX_PLAYERS, VERSION, NAME, STATE, BOARD) values (0, 3, 0, 'test1', 1, '{
  "width": 11,
  "maxPlayers": 2,
  "height": 8,
  "boardName": "Burnout",
  "playerAmount": 2,
  "number": 1,
  "priorityAntenna": {
    "x": 0,
    "y": 3,
    "background": "Empty",
    "walls": []
  },
  "spaces": [
    [
      {
        "type": "Space",
        "x": 0,
        "y": 0,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "Space",
        "x": 0,
        "y": 1,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "Space",
        "x": 0,
        "y": 2,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "Priority",
        "x": 0,
        "y": 3,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "Space",
        "x": 0,
        "y": 4,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "Space",
        "x": 0,
        "y": 5,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "Space",
        "x": 0,
        "y": 6,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "Space",
        "x": 0,
        "y": 7,
        "background": "Empty",
        "walls": []
      }
    ],
    [
      {
        "type": "Spawn",
        "number": 1,
        "x": 1,
        "y": 0,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "Space",
        "x": 1,
        "y": 1,
        "background": "Empty",
        "walls": [
          "NORTH"
        ]
      },
      {
        "type": "Space",
        "x": 1,
        "y": 2,
        "background": "Empty",
        "walls": [
          "EAST"
        ]
      },
      {
        "type": "Space",
        "x": 1,
        "y": 3,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "Space",
        "x": 1,
        "y": 4,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "Space",
        "x": 1,
        "y": 5,
        "background": "Empty",
        "walls": [
          "EAST"
        ]
      },
      {
        "type": "Space",
        "x": 1,
        "y": 6,
        "background": "Empty",
        "walls": [
          "SOUTH"
        ]
      },
      {
        "type": "Spawn",
        "number": 2,
        "x": 1,
        "y": 7,
        "background": "Empty",
        "walls": []
      }
    ],
    [
      {
        "type": "Space",
        "x": 2,
        "y": 0,
        "background": "Empty",
        "walls": [
          "EAST"
        ]
      },
      {
        "type": "Space",
        "x": 2,
        "y": 1,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "Space",
        "x": 2,
        "y": 2,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "Space",
        "x": 2,
        "y": 3,
        "background": "Empty",
        "walls": [
          "EAST"
        ]
      },
      {
        "type": "Space",
        "x": 2,
        "y": 4,
        "background": "Empty",
        "walls": [
          "EAST"
        ]
      },
      {
        "type": "Space",
        "x": 2,
        "y": 5,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "Space",
        "x": 2,
        "y": 6,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "Space",
        "x": 2,
        "y": 7,
        "background": "Empty",
        "walls": [
          "EAST"
        ]
      }
    ],
    [
      {
        "type": "Checkpoint",
        "previous": {
          "players": [],
          "number": 1,
          "x": 10,
          "y": 4,
          "background": "Empty",
          "walls": []
        },
        "players": [],
        "number": 2,
        "x": 3,
        "y": 0,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "Conveyor",
        "direction": "WEST",
        "x": 3,
        "y": 1,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "Space",
        "x": 3,
        "y": 2,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "Pit",
        "x": 3,
        "y": 3,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "Reboot",
        "direction": "EAST",
        "x": 3,
        "y": 4,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "Space",
        "x": 3,
        "y": 5,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "FastConveyorbelt",
        "number": 2,
        "direction": "EAST",
        "x": 3,
        "y": 6,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "Energy",
        "energy": true,
        "x": 3,
        "y": 7,
        "background": "Empty",
        "walls": []
      }
    ],
    [
      {
        "type": "FastConveyorbelt",
        "number": 2,
        "direction": "SOUTH",
        "x": 4,
        "y": 0,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "Space",
        "x": 4,
        "y": 1,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "Space",
        "x": 4,
        "y": 2,
        "background": "Empty",
        "walls": [
          "NORTH"
        ]
      },
      {
        "type": "Space",
        "x": 4,
        "y": 3,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "Space",
        "x": 4,
        "y": 4,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "Lazer",
        "direction": "NORTH",
        "x": 4,
        "y": 5,
        "background": "Empty",
        "walls": [
          "SOUTH"
        ]
      },
      {
        "type": "Space",
        "x": 4,
        "y": 6,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "Conveyor",
        "direction": "SOUTH",
        "x": 4,
        "y": 7,
        "background": "Empty",
        "walls": []
      }
    ],
    [
      {
        "type": "Space",
        "x": 5,
        "y": 0,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "Lazer",
        "direction": "EAST",
        "x": 5,
        "y": 1,
        "background": "Empty",
        "walls": [
          "WEST"
        ]
      },
      {
        "type": "FastConveyorbelt",
        "number": 2,
        "direction": "NORTH",
        "turn": "WEST",
        "x": 5,
        "y": 2,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "FastConveyorbelt",
        "number": 2,
        "direction": "NORTH",
        "x": 5,
        "y": 3,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "FastConveyorbelt",
        "number": 2,
        "direction": "NORTH",
        "x": 5,
        "y": 4,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "FastConveyorbelt",
        "number": 2,
        "direction": "WEST",
        "turn": "WEST",
        "x": 5,
        "y": 5,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "Space",
        "x": 5,
        "y": 6,
        "background": "Empty",
        "walls": [
          "WEST"
        ]
      },
      {
        "type": "Space",
        "x": 5,
        "y": 7,
        "background": "Empty",
        "walls": []
      }
    ],
    [
      {
        "type": "Pit",
        "x": 6,
        "y": 0,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "Space",
        "x": 6,
        "y": 1,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "FastConveyorbelt",
        "number": 2,
        "direction": "EAST",
        "x": 6,
        "y": 2,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "Gear",
        "direction": "EAST",
        "x": 6,
        "y": 3,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "Space",
        "x": 6,
        "y": 4,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "FastConveyorbelt",
        "number": 2,
        "direction": "WEST",
        "x": 6,
        "y": 5,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "Space",
        "x": 6,
        "y": 6,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "Energy",
        "energy": true,
        "x": 6,
        "y": 7,
        "background": "Empty",
        "walls": []
      }
    ],
    [
      {
        "type": "Energy",
        "energy": true,
        "x": 7,
        "y": 0,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "Space",
        "x": 7,
        "y": 1,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "FastConveyorbelt",
        "number": 2,
        "direction": "EAST",
        "x": 7,
        "y": 2,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "Space",
        "x": 7,
        "y": 3,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "Energy",
        "energy": true,
        "x": 7,
        "y": 4,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "FastConveyorbelt",
        "number": 2,
        "direction": "WEST",
        "x": 7,
        "y": 5,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "Space",
        "x": 7,
        "y": 6,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "Pit",
        "x": 7,
        "y": 7,
        "background": "Empty",
        "walls": []
      }
    ],
    [
      {
        "type": "Space",
        "x": 8,
        "y": 0,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "Space",
        "x": 8,
        "y": 1,
        "background": "Empty",
        "walls": [
          "EAST"
        ]
      },
      {
        "type": "FastConveyorbelt",
        "number": 2,
        "direction": "EAST",
        "turn": "WEST",
        "x": 8,
        "y": 2,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "FastConveyorbelt",
        "number": 2,
        "direction": "SOUTH",
        "x": 8,
        "y": 3,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "FastConveyorbelt",
        "number": 2,
        "direction": "SOUTH",
        "x": 8,
        "y": 4,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "FastConveyorbelt",
        "number": 2,
        "direction": "SOUTH",
        "turn": "WEST",
        "x": 8,
        "y": 5,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "Lazer",
        "direction": "WEST",
        "x": 8,
        "y": 6,
        "background": "Empty",
        "walls": [
          "EAST"
        ]
      },
      {
        "type": "Space",
        "x": 8,
        "y": 7,
        "background": "Empty",
        "walls": []
      }
    ],
    [
      {
        "type": "Conveyor",
        "direction": "NORTH",
        "x": 9,
        "y": 0,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "Space",
        "x": 9,
        "y": 1,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "Lazer",
        "direction": "SOUTH",
        "x": 9,
        "y": 2,
        "background": "Empty",
        "walls": [
          "NORTH"
        ]
      },
      {
        "type": "Space",
        "x": 9,
        "y": 3,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "Space",
        "x": 9,
        "y": 4,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "Space",
        "x": 9,
        "y": 5,
        "background": "Empty",
        "walls": [
          "SOUTH"
        ]
      },
      {
        "type": "Space",
        "x": 9,
        "y": 6,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "FastConveyorbelt",
        "number": 2,
        "direction": "NORTH",
        "x": 9,
        "y": 7,
        "background": "Empty",
        "walls": []
      }
    ],
    [
      {
        "type": "Energy",
        "energy": true,
        "x": 10,
        "y": 0,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "FastConveyorbelt",
        "number": 2,
        "direction": "WEST",
        "x": 10,
        "y": 1,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "Space",
        "x": 10,
        "y": 2,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "Pit",
        "x": 10,
        "y": 3,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "Checkpoint",
        "players": [],
        "number": 1,
        "x": 10,
        "y": 4,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "Space",
        "x": 10,
        "y": 5,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "Conveyor",
        "direction": "EAST",
        "x": 10,
        "y": 6,
        "background": "Empty",
        "walls": []
      },
      {
        "type": "Checkpoint",
        "previous": {
          "previous": {
            "players": [],
            "number": 1,
            "x": 10,
            "y": 4,
            "background": "Empty",
            "walls": []
          },
          "players": [],
          "number": 2,
          "x": 3,
          "y": 0,
          "background": "Empty",
          "walls": []
        },
        "players": [],
        "number": 3,
        "x": 10,
        "y": 7,
        "background": "Empty",
        "walls": []
      }
    ]
  ],
  "players": [
    {
      "isRebooting": false,
      "name": "adsf",
      "color": "red",
      "space": {
        "type": "Spawn",
        "number": 1,
        "x": 1,
        "y": 0,
        "background": "Empty",
        "walls": []
      },
      "priority": 0,
      "heading": "EAST",
      "deck": {
        "deck": [
          {
            "cardtype": "CommandCard",
            "command": "UTURN",
            "type": "Command"
          },
          {
            "cardtype": "CommandCard",
            "command": "OPTION_LEFT_RIGHT",
            "type": "Command"
          },
          {
            "cardtype": "CommandCard",
            "command": "AGAIN",
            "type": "Command"
          },
          {
            "cardtype": "CommandCard",
            "command": "FAST_FORWARD",
            "type": "Command"
          },
          {
            "cardtype": "CommandCard",
            "command": "FAST_FORWARD",
            "type": "Command"
          },
          {
            "cardtype": "CommandCard",
            "command": "FAST_FORWARD",
            "type": "Command"
          },
          {
            "cardtype": "CommandCard",
            "command": "REVERSE",
            "type": "Command"
          },
          {
            "cardtype": "CommandCard",
            "command": "FORWARD",
            "type": "Command"
          },
          {
            "cardtype": "CommandCard",
            "command": "FORWARD",
            "type": "Command"
          },
          {
            "cardtype": "CommandCard",
            "command": "FORWARD",
            "type": "Command"
          },
          {
            "cardtype": "CommandCard",
            "command": "MOVE_3",
            "type": "Command"
          },
          {
            "cardtype": "CommandCard",
            "command": "FORWARD",
            "type": "Command"
          },
          {
            "cardtype": "CommandCard",
            "command": "LEFT",
            "type": "Command"
          },
          {
            "cardtype": "CommandCard",
            "command": "FAST_FORWARD",
            "type": "Command"
          },
          {
            "cardtype": "CommandCard",
            "command": "FORWARD",
            "type": "Command"
          },
          {
            "cardtype": "CommandCard",
            "command": "FAST_FORWARD",
            "type": "Command"
          },
          {
            "cardtype": "CommandCard",
            "command": "RIGHT",
            "type": "Command"
          },
          {
            "cardtype": "CommandCard",
            "command": "FORWARD",
            "type": "Command"
          },
          {
            "cardtype": "CommandCard",
            "command": "FAST_FORWARD",
            "type": "Command"
          }
        ],
        "discards": []
      },
      "energy": 0,
      "program": [
        {
          "visible": true
        },
        {
          "visible": true
        },
        {
          "visible": true
        },
        {
          "visible": true
        },
        {
          "visible": true
        }
      ],
      "cards": [
        {
          "visible": true
        },
        {
          "visible": true
        },
        {
          "visible": true
        },
        {
          "visible": true
        },
        {
          "visible": true
        },
        {
          "visible": true
        },
        {
          "visible": true
        },
        {
          "visible": true
        }
      ]
    }
  ],
  "phase": "INITIALISATION",
  "step": 0,
  "stepMode": false,
  "wincondition": {
    "previous": {
      "previous": {
        "players": [],
        "number": 1,
        "x": 10,
        "y": 4,
        "background": "Empty",
        "walls": []
      },
      "players": [],
      "number": 2,
      "x": 3,
      "y": 0,
      "background": "Empty",
      "walls": []
    },
    "players": [],
    "number": 3,
    "x": 10,
    "y": 7,
    "background": "Empty",
    "walls": []
  },
  "gameLog": [],
  "spawnPriority": [
    {
      "number": 2,
      "x": 1,
      "y": 7,
      "background": "Empty",
      "walls": []
    }
  ],
  "boardActions": [
    {
      "number": 2,
      "direction": "EAST",
      "x": 3,
      "y": 6,
      "background": "Empty",
      "walls": []
    },
    {
      "direction": "WEST",
      "x": 3,
      "y": 1,
      "background": "Empty",
      "walls": []
    },
    {
      "direction": "EAST",
      "x": 6,
      "y": 3,
      "background": "Empty",
      "walls": []
    },
    {
      "direction": "NORTH",
      "x": 4,
      "y": 5,
      "background": "Empty",
      "walls": [
        "SOUTH"
      ]
    },
    {
      "energy": true,
      "x": 3,
      "y": 7,
      "background": "Empty",
      "walls": []
    },
    {
      "players": [],
      "number": 1,
      "x": 10,
      "y": 4,
      "background": "Empty",
      "walls": []
    }
  ],
  "rebootToken": {
    "direction": "EAST",
    "x": 3,
    "y": 4,
    "background": "Empty",
    "walls": []
  },
  "pit": {
    "x": 10,
    "y": 3,
    "background": "Empty",
    "walls": []
  },
  "playerOrder": []
}'), (0, 4, 0, 'Not a test', 1, '');

INSERT INTO BOARD (ID, GAME_ID, CLIENT_BOARD) values (1, 1, ''), (2,2,'');